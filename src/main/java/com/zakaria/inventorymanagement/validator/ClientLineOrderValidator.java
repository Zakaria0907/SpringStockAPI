package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClientLineOrderValidator {
	public static List<String> validateClientLineOrder(ClientLineOrderDto clientLineOrderDto) {
		List<String> errors = new ArrayList<>();
		
		if (clientLineOrderDto == null) {
			errors.add("Please provide the line order details");
			errors.add("Quantity is required");
			errors.add("Unit price is required");
			errors.add("Item is required");
			errors.add("Client order is required");
			return errors;
		}
		
		if (clientLineOrderDto.getQuantity() == null || clientLineOrderDto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please enter a valid quantity greater than zero");
		}
		
		if (clientLineOrderDto.getUnitPrice() == null || clientLineOrderDto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please enter a valid unit price greater than zero");
		}
		
		if (clientLineOrderDto.getItem() == null || clientLineOrderDto.getItem().getId() == null) {
			errors.add("Item ID is required");
		}
		
		if (clientLineOrderDto.getClientOrder() == null || clientLineOrderDto.getClientOrder().getId() == null) {
			errors.add("Client order ID is required");
		}
		return errors;
	}
}
