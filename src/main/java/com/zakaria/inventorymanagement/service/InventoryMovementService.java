package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import java.math.BigDecimal;
import java.util.List;

public interface InventoryMovementService {
	
	BigDecimal getRealTimeStock(Integer itemId);
	
	List<InventoryMovementDto> getStockMovements(Integer itemId);
	
	InventoryMovementDto enterStock(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto exitStock(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto positiveStockCorrection(InventoryMovementDto inventoryMovementDto);
	
	InventoryMovementDto negativeStockCorrection(InventoryMovementDto inventoryMovementDto);
	
}
