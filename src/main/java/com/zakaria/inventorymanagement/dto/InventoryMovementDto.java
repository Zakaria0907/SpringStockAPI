package com.zakaria.inventorymanagement.dto;

import com.zakaria.inventorymanagement.entity.enums.MovementSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

import com.zakaria.inventorymanagement.entity.enums.MovementType;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryMovementDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private Instant movementDate;
	
	private BigDecimal quantity;
	
	private MovementType movementType;
	
	private MovementSource movementSource;
	
	private ItemDto item;
}
