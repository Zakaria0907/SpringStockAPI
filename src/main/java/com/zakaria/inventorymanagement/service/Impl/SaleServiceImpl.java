package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.dto.SaleDto;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.entity.Item;
import com.zakaria.inventorymanagement.entity.SaleLineItem;
import com.zakaria.inventorymanagement.entity.enums.MovementSource;
import com.zakaria.inventorymanagement.entity.enums.MovementType;
import com.zakaria.inventorymanagement.entity.Sale;
import com.zakaria.inventorymanagement.mapper.impl.ItemMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.SaleLineItemMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.SaleMapperImpl;
import com.zakaria.inventorymanagement.repository.ItemRepository;
import com.zakaria.inventorymanagement.repository.SaleLineItemRepository;
import com.zakaria.inventorymanagement.repository.SaleRepository;
import com.zakaria.inventorymanagement.service.InventoryMovementService;
import com.zakaria.inventorymanagement.service.SaleService;
import com.zakaria.inventorymanagement.validator.SaleValidator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SaleServiceImpl implements SaleService {
	
	private ItemRepository itemRepository;
	private SaleRepository saleRepository;
	private SaleLineItemRepository saleLineItemRepository;
	private InventoryMovementService inventoryMovementService;
	
	private SaleMapperImpl saleMapper;
	private SaleLineItemMapperImpl saleLineItemMapper;
	private ItemMapperImpl itemMapper;
	
	@Autowired
	public SaleServiceImpl(ItemRepository itemRepository, SaleRepository saleRepository,
	                       SaleLineItemRepository saleLineItemRepository,
	                       InventoryMovementService inventoryMovementService,
	                       SaleMapperImpl saleMapper,
	                       SaleLineItemMapperImpl saleLineItemMapper,
	                       ItemMapperImpl itemMapper) {
		this.itemRepository = itemRepository;
		this.saleRepository = saleRepository;
		this.saleLineItemRepository = saleLineItemRepository;
		this.inventoryMovementService = inventoryMovementService;
		this.saleMapper = saleMapper;
		this.saleLineItemMapper = saleLineItemMapper;
		this.itemMapper = itemMapper;
	}
	
	@Override
	public SaleDto save(SaleDto dto) {
		List<String> errors = SaleValidator.validateSale(dto);
		if (!errors.isEmpty()) {
			Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
			logger.error("Sale is not valid");
			throw new InvalidEntityException("The sale object is not valid", ErrorCodes.SALE_NOT_VALID, errors);
		}
		
		List<String> itemErrors = new ArrayList<>();
		
		dto.getSaleLineItems().forEach(saleLineItemDto -> {
			Optional<Item> item = itemRepository.findById(saleLineItemDto.getItem().getId());
			if (item.isEmpty()) {
				itemErrors.add("No item with ID " + saleLineItemDto.getItem().getId() + " was found in the database");
			}
		});
		
		if (!itemErrors.isEmpty()) {
			Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
			logger.error("One or more items were not found in the DB, {}", errors);
			throw new InvalidEntityException("One or more items were not found in the database", ErrorCodes.SALE_NOT_VALID, errors);
		}
		
		Sale savedSale = saleRepository.save(saleMapper.mapFrom(dto));
		
		dto.getSaleLineItems().forEach(saleLineItemDto -> {
			SaleLineItem saleLineItem = saleLineItemMapper.mapFrom(saleLineItemDto);
			saleLineItem.setSale(savedSale);
			saleLineItemRepository.save(saleLineItem);
			updateInventoryMovement(saleLineItem);
		});
		
		return saleMapper.mapTo(savedSale);
	}
	
	@Override
	public SaleDto findById(Integer id) {
		if (id == null) {
			Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
			logger.error("Sale ID is NULL");
			return null;
		}
		return saleRepository.findById(id)
				.map(saleMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException("No sale was found in the database with ID " + id, ErrorCodes.SALE_NOT_FOUND));
	}
	
	@Override
	public SaleDto findByCode(String code) {
		if (!StringUtils.hasLength(code)) {
			Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
			logger.error("Sale CODE is NULL");
			return null;
		}
		return saleRepository.findSaleByCode(code)
				.map(saleMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No sale was found in the database with the CODE " + code, ErrorCodes.SALE_NOT_VALID
				));
	}
	
	@Override
	public List<SaleDto> findAll() {
		return saleRepository.findAll().stream()
				.map(saleMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
			logger.error("Sale ID is NULL");
			return;
		}
		List<SaleLineItem> saleLineItems = saleLineItemRepository.findAllBySaleId(id);
		if (!saleLineItems.isEmpty()) {
			throw new InvalidOperationException("Impossible to delete a sale that is in use",
					ErrorCodes.SALE_ALREADY_IN_USE);
		}
		saleRepository.deleteById(id);
	}
	
	
	private void updateInventoryMovement(SaleLineItem saleLineItem) {
		InventoryMovementDto inventoryMovementDto = InventoryMovementDto.builder()
				.item(itemMapper.mapTo(saleLineItem.getItem()))
				.movementDate(Instant.now())
				.movementType(MovementType.EXIT)
				.movementSource(MovementSource.SALE)
				.quantity(saleLineItem.getQuantity())
				.companyId(saleLineItem.getCompanyId())
				.build();
		inventoryMovementService.exitInventory(inventoryMovementDto);
	}
}
