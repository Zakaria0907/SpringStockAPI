package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class InventoryMovementValidator {
	
	public static List<String> validateInventoryMovement(InventoryMovementDto inventoryMovementDto) {
		List<String> errors = new ArrayList<>();
		if (inventoryMovementDto == null) {
			errors.add("Please provide the movement date");
			errors.add("Please provide the movement quantity");
			errors.add("Please provide the item details");
			errors.add("Please provide the type of movement");
			return errors;
		}
		
		if (inventoryMovementDto.getMovementDate() == null) {
			errors.add("Please provide the movement date");
		}
		if (inventoryMovementDto.getQuantity() == null || inventoryMovementDto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please provide a valid movement quantity");
		}
		if (inventoryMovementDto.getItem() == null || inventoryMovementDto.getItem().getId() == null) {
			errors.add("Please provide the item details");
		}
		if (inventoryMovementDto.getMovementType() == null || !StringUtils.hasLength(inventoryMovementDto.getMovementType().name())) {
			errors.add("Please provide the type of movement");
		}
		
		return errors;
	}
}
