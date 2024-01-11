package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface InventoryMovementService {
	
	BigDecimal getRealTimeStock(Integer itemId);
	
	List<InventoryMovementDto> getStockMovements(Integer itemId);
	
	InventoryMovementDto enterInventory(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto exitInventory(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto positiveStockCorrection(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto negativeStockCorrection(InventoryMovementDto inventoryMovementDto);
	
}
