package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProviderOrderValidator {
	
	public static List<String> validateProviderOrder(ProviderOrderDto providerOrderDto) {
		List<String> errors = new ArrayList<>();
		if (providerOrderDto == null) {
			errors.add("Please provide the order ID");
			errors.add("Please provide the order code");
			errors.add("Please provide the order date");
			errors.add("Please provide the provider details");
			errors.add("Please provide the provider line orders");
			return errors;
		}
		
		if (providerOrderDto.getId() == null) {
			errors.add("Please provide the order ID");
		}
		if (!StringUtils.hasLength(providerOrderDto.getCode())) {
			errors.add("Please provide the order code");
		}
		if (providerOrderDto.getOrderDate() == null) {
			errors.add("Please provide the order date");
		}
		if (providerOrderDto.getProvider() == null || providerOrderDto.getProvider().getId() == null) {
			errors.add("Please provide the provider details");
		}
//		if (providerOrderDto.getProviderLineOrders() == null || providerOrderDto.getProviderLineOrders().isEmpty()) {
//			errors.add("Please provide the provider line orders");
//		}
		
		return errors;
	}
}
