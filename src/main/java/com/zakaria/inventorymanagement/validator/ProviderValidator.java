package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ClientDto;
import com.zakaria.inventorymanagement.dto.ProviderDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProviderValidator {
	public static List<String> validateProvider(ProviderDto providerDto) {
		List<String> errors = new ArrayList<>();
		
		if (providerDto == null) {
			errors.add("Please enter lastname");
			errors.add("Please enter firstname");
			errors.add("Please enter email");
			errors.add("Please enter phone number");
			return errors;
			
		}
		
		if (!StringUtils.hasLength(providerDto.getLastname())) {
			errors.add("Please enter lastname");
		}
		
		if (!StringUtils.hasLength(providerDto.getFirstname())) {
			errors.add("Please enter firstname");
		}
		
		if (!StringUtils.hasLength(providerDto.getEmail())) {
			errors.add("Please enter email");
		}
		
		if (!StringUtils.hasLength(providerDto.getPhoneNumber())) {
			errors.add("Please enter phone number");
		}
		return errors;
	}
}
