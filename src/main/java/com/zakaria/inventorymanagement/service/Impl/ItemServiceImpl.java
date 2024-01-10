package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.*;
import com.zakaria.inventorymanagement.entity.SaleLineItem;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import com.zakaria.inventorymanagement.entity.Sale;
import com.zakaria.inventorymanagement.mapper.impl.*;
import com.zakaria.inventorymanagement.repository.*;
import com.zakaria.inventorymanagement.service.ItemService;
import com.zakaria.inventorymanagement.validator.ItemValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {
	
	private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	private ItemRepository itemRepository;
	private SaleLineItemRepository saleLineItemRepository;
	private ClientLineOrderRepository clientLineOrderRepository;
	private ProviderLineOrderRepository providerLineOrderRepository;
	
	private ClientOrderRepository clientOrderRepository;
	private ProviderOrderRepository providerOrderRepository;
	
	private ItemMapperImpl itemMapper;
	private SaleLineItemMapperImpl saleLineItemMapper;
	private ClientLineOrderMapperImpl clientLineOrderMapper;
	private ProviderLineOrderMapperImpl providerLineOrderMapper;
	
	@Autowired
	public ItemServiceImpl(
			ItemRepository itemRepository,
			SaleLineItemRepository saleLineItemRepository,
			ClientLineOrderRepository clientLineOrderRepository,
			ProviderLineOrderRepository providerLineOrderRepository,
			ItemMapperImpl itemMapper,
			SaleLineItemMapperImpl saleLineItemMapper) {
		this.itemRepository = itemRepository;
		this.saleLineItemRepository = saleLineItemRepository;
		this.clientLineOrderRepository = clientLineOrderRepository;
		this.providerLineOrderRepository = providerLineOrderRepository;
		this.itemMapper = itemMapper;
		this.saleLineItemMapper = saleLineItemMapper;
	}
	
	@Override
	public ItemDto save(ItemDto itemDto) {
		List<String> errors = ItemValidator.validateItem(itemDto);
		if (!errors.isEmpty()) {
			log.error("Item is not valid {}", itemDto);
			throw new InvalidEntityException("Invalid item", ErrorCodes.ITEM_NOT_VALID, errors);
		}
		
		return itemMapper.mapTo(
				itemRepository.save(
						itemMapper.mapFrom(itemDto)
				)
		);
	}
	
	@Override
	public ItemDto findById(Integer id) {
		if (id == null) {
			log.error("Item ID is null");
			return null;
		}
		
		return itemRepository.findById(id).map(itemMapper::mapTo).orElseThrow(() ->
				new EntityNotFoundException(
						"No item with ID = " + id + " found in DB",
						ErrorCodes.ITEM_NOT_FOUND)
		);
	}
	
	@Override
	public ItemDto findByItemCode(String codeItem) {
		if (!StringUtils.hasLength(codeItem)) {
			log.error("Item CODE is null");
			return null;
		}
		
		return itemRepository.findByItemCode(codeItem)
				.map(itemMapper::mapTo)
				.orElseThrow(() ->
						new EntityNotFoundException(
								"No item with code = " + codeItem + "found in DB",
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
	public List<SaleLineItemDto> findSaleHistory(Integer idItem) {
		return saleLineItemRepository.findAllByItemId(idItem).stream()
				.map(saleLineItemMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ClientLineOrderDto> findClientOrderHistory(Integer idItem) {
		return clientLineOrderRepository.findAllByItemId(idItem) .stream()
				.map(clientLineOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ProviderLineOrderDto> findProviderOrderHistory(Integer idItem) {
		return providerLineOrderRepository.findAllByItemId(idItem).stream()
				.map(providerLineOrderMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ItemDto> findAllItemsByCategoryId(Integer idCategory) {
		return itemRepository.findAllByCategoryId(idCategory).stream()
				.map(itemMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Item ID is null");
			return;
		}
		List<ClientLineOrder> clientLineOrders = clientLineOrderRepository.findAllByItemId(id);
		if (!clientLineOrders.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item already used in client orders", ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		List<ProviderLineOrder> providerLineOrders = providerLineOrderRepository.findAllByItemId(id);
		if (!providerLineOrders.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item already used in provider orders",
					ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		List<SaleLineItem> saleLineItems = saleLineItemRepository.findAllByItemId(id);
		if (!saleLineItems.isEmpty()) {
			throw new InvalidOperationException("Cannot delete an item already used in sales",
					ErrorCodes.ITEM_ALREADY_IN_USE);
		}
		itemRepository.deleteById(id);
	}
	
	// Additional methods go here, ensure you replace the DTOs, entities, and repository methods to match those available in your project.
}