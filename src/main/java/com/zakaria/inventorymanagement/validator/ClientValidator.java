package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
	public static List<String> validateClient(ClientDto clientDto) {
		List<String> errors = new ArrayList<>();
		
		if (clientDto == null) {
			errors.add("Please enter lastname");
			errors.add("Please enter firstname");
			errors.add("Please enter email");
			errors.add("Please enter phone number");
			errors.addAll(AddressValidator.validateAddress(null));
			return errors;
			
		}
		
		if (!StringUtils.hasLength(clientDto.getLastname())) {
			errors.add("Please enter lastname");
		}
		
		if (!StringUtils.hasLength(clientDto.getFirstname())) {
			errors.add("Please enter firstname");
		}
		
		if (!StringUtils.hasLength(clientDto.getEmail())) {
			errors.add("Please enter email");
		}
		
		if (!StringUtils.hasLength(clientDto.getPhoneNumber())) {
			errors.add("Please enter phone number");
		}
		
		errors.addAll(AddressValidator.validateAddress(clientDto.getAddress()));
		return errors;
	}
}
