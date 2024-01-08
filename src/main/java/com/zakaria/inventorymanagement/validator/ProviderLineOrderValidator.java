package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class ProviderLineOrderValidator {
	
	public static List<String> validateProviderLineOrder(ProviderLineOrderDto providerLineOrderDto) {
		List<String> errors = new ArrayList<>();
		if (providerLineOrderDto == null) {
			errors.add("Please provide the company ID");
			errors.add("Please provide the line order ID");
			errors.add("Please provide the quantity");
			errors.add("Please provide the unit price");
			errors.add("Please provide the item details");
			errors.add("Please provide the provider order details");
			return errors;
		}
		
		if (providerLineOrderDto.getQuantity() == null || providerLineOrderDto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please provide a valid quantity");
		}
		if (providerLineOrderDto.getUnitPrice() == null || providerLineOrderDto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please provide a valid unit price");
		}
		if (providerLineOrderDto.getItem() == null || providerLineOrderDto.getItem().getId() == null) {
			errors.add("Please provide the item details");
		}
		if (providerLineOrderDto.getProviderOrder() == null || providerLineOrderDto.getProviderOrder().getId() == null) {
			errors.add("Please provide the provider order details");
		}
		
		return errors;
	}
}
