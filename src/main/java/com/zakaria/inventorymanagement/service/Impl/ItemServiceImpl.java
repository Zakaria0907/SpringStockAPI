package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.*;
import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import com.zakaria.inventorymanagement.entity.SaleLineItem;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.*;
import com.zakaria.inventorymanagement.repository.*;
import com.zakaria.inventorymanagement.service.ItemService;
import com.zakaria.inventorymanagement.validator.ItemValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
	
	private ItemRepository itemRepository;
	private SaleLineItemRepository saleLineItemRepository;
	private ProviderLineOrderRepository providerLineOrderRepository;
	private ClientLineOrderRepository clientLineOrderRepository;
	
	private ItemMapperImpl itemMapper;
	private SaleLineItemMapperImpl saleLineItemMapper;
	private ClientLineOrderMapperImpl clientLineOrderMapper;
	private ProviderLineOrderMapperImpl providerLineOrderMapper;
	
	@Autowired
	public ItemServiceImpl(ItemRepository itemRepository,
	                       SaleLineItemRepository saleLineItemRepository,
	                       ProviderLineOrderRepository providerLineOrderRepository,
	                       ClientLineOrderRepository clientLineOrderRepository,
	                       ItemMapperImpl itemMapper,
	                       SaleLineItemMapperImpl saleLineItemMapper,
	                       ClientLineOrderMapperImpl clientLineOrderMapper,
	                       ProviderLineOrderMapperImpl providerLineOrderMapper) {
		this.itemRepository = itemRepository;
		this.saleLineItemRepository = saleLineItemRepository;
		this.providerLineOrderRepository = providerLineOrderRepository;
		this.clientLineOrderRepository = clientLineOrderRepository;
		this.itemMapper = itemMapper;
		this.saleLineItemMapper = saleLineItemMapper;
		this.clientLineOrderMapper = clientLineOrderMapper;
		this.providerLineOrderMapper = providerLineOrderMapper;
	}
	
	@Override
	public ItemDto save(ItemDto itemDto) {
		List<String> errors = ItemValidator.validateItem(itemDto);
		if (!errors.isEmpty()) {
			log.error("Item is not valid {}", itemDto);
			throw new InvalidEntityException("The item is not valid", ErrorCodes.ITEM_NOT_VALID, errors);
		}
		
		return itemMapper.mapTo(itemRepository.save(itemMapper.mapFrom(itemDto)));
	}
	
	@Override
	public ItemDto findById(Integer id) {
		if (id == null) {
			log.error("Item ID is null");
			return null;
		}
		
		return itemRepository.findById(id)
				.map(itemMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No item with ID = " + id + " was found in the database",
						ErrorCodes.ITEM_NOT_FOUND)
				);
	}
	
	@Override
	public ItemDto findByItemCode(String itemCode) {
		if (!StringUtils.hasLength(itemCode)) {
			log.error("Item CODE is null");
			return null;
		}
		
		return itemRepository.findByItemCode(itemCode)
				.map(itemMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No item with CODE = " + itemCode + " was found in the database",
						ErrorCodes.ITEM_NOT_FOUND)
				);
	}
	
	@Override
	public List<ItemDto> findAll() {
		return itemRepository.findAll().stream()
				.map(itemMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<SaleLineItemDto> findSaleHistory(Integer itemId) {
		return saleLineItemRepository.findAllByItemId(itemId).stream()
				.map(saleLineItemMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ClientLineOrderDto> findClientOrderHistory(Integer itemId) {
		return clientLineOrderRepository.findAllByItemId(itemId).stream()
				.map(clientLineOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ProviderLineOrderDto> findProviderOrderHistory(Integer itemId) {
		return providerLineOrderRepository.findAllByItemId(itemId).stream()
				.map(providerLineOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ItemDto> findAllItemsByCategoryId(Integer categoryId) {
		return itemRepository.findAllByCategoryId(categoryId).stream()
				.map(itemMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Item ID is null");
			return;
		}
		List<ClientLineOrder> clientOrderLines = clientLineOrderRepository.findAllByItemId(id);
		if (!clientOrderLines.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item that is used in client orders",
					ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		List<ProviderLineOrder> providerOrderLines = providerLineOrderRepository.findAllByItemId(id);
		if (!providerOrderLines.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item that is used in provider orders",
					ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		List<SaleLineItem> saleLineItems = saleLineItemRepository.findAllByItemId(id);
		if (!saleLineItems.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item that is used in sales",
					ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		itemRepository.deleteById(id);
	}
	
}
