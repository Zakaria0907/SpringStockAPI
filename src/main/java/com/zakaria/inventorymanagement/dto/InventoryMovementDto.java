package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

import com.zakaria.inventorymanagement.entity.MovementType;

@Data
@Builder
public class InventoryMovementDto {
	
	private Integer id;
	
	private Instant movementDate;
	
	private BigDecimal quantity;
	
	private MovementType movementType; // Assuming you have a MovementTypeDto based on your MovementType enumeration.
	
	private ItemDto item;
}
