package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.entity.enums.MovementType;
import com.zakaria.inventorymanagement.mapper.impl.InventoryMovementMapperImpl;
import com.zakaria.inventorymanagement.repository.InventoryMovementRepository;
import com.zakaria.inventorymanagement.service.InventoryMovementService;
import com.zakaria.inventorymanagement.service.ItemService;
import com.zakaria.inventorymanagement.validator.InventoryMovementValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryMovementServiceImpl implements InventoryMovementService {
	
	private InventoryMovementRepository inventoryMovementRepository;
	private ItemService itemService;
	private InventoryMovementMapperImpl inventoryMovementMapper;
	
	public InventoryMovementServiceImpl(InventoryMovementRepository inventoryMovementRepository, ItemService itemService) {
		this.inventoryMovementRepository = inventoryMovementRepository;
		this.itemService = itemService;
	}
	
	@Override
	public BigDecimal getRealTimeStock(Integer itemId) {
		if (itemId == null) {
			log.warn("Item ID is NULL");
			return BigDecimal.valueOf(-1);
		}
		itemService.findById(itemId);
		return inventoryMovementRepository.realInventoryStock(itemId);
	}
	
	
	@Override
	public List<InventoryMovementDto> getStockMovements(Integer itemId) {
		if (itemId == null) {
			log.warn("Item ID is NULL");
			return Collections.emptyList();
		}
		return inventoryMovementRepository.findAllByItemId(itemId).stream()
				.map(inventoryMovementMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public InventoryMovementDto enterInventory(InventoryMovementDto inventoryMovementDto) {
		return enterPositive(inventoryMovementDto, MovementType.ENTRY);
	}
	
	
	@Override
	public InventoryMovementDto exitInventory(InventoryMovementDto inventoryMovementDto) {
		return exitNegative(inventoryMovementDto, MovementType.EXIT);
	}
	
	
	@Override
	public InventoryMovementDto positiveStockCorrection(InventoryMovementDto inventoryMovementDto) {
		return enterPositive(inventoryMovementDto, MovementType.ADJUSTMENT_POS);
	}
	
	
	
	@Override
	public InventoryMovementDto negativeStockCorrection(InventoryMovementDto inventoryMovementDto) {
		return exitNegative(inventoryMovementDto, MovementType.ADJUSTMENT_NEG);
	}
	
	private InventoryMovementDto enterPositive(InventoryMovementDto dto, MovementType movementType) {
		InventoryMovementValidator.validateInventoryMovement(dto);
		dto.setQuantity(BigDecimal.valueOf(Math.abs(dto.getQuantity().doubleValue())));
		dto.setMovementType(movementType);
		return inventoryMovementMapper.mapTo(
				inventoryMovementRepository.save(inventoryMovementMapper.mapFrom(dto))
		);
	}
	
	private InventoryMovementDto exitNegative(InventoryMovementDto dto, MovementType movementType) {
		InventoryMovementValidator.validateInventoryMovement(dto);
		dto.setQuantity(BigDecimal.valueOf(Math.abs(dto.getQuantity().doubleValue()) * -1));
		dto.setMovementType(movementType);
		return inventoryMovementMapper.mapTo(
				inventoryMovementRepository.save(inventoryMovementMapper.mapFrom(dto))
		);
	}
	
	
	
}
