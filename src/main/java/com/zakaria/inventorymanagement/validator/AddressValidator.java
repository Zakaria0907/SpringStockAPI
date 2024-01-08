package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.AddressDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressValidator {
	public static List<String> validateAddress(AddressDto addressDto) {
		List<String> errors = new ArrayList<>();
		
		if (addressDto == null) {
			errors.add("Please enter address line 1");
			errors.add("Please enter city");
			errors.add("Please enter zip code");
			errors.add("Please enter country");
			return errors;
		}
		
		if (!StringUtils.hasLength(addressDto.getAddress1())) {
			errors.add("Please enter address line 1");
		}
		
		if (!StringUtils.hasLength(addressDto.getCity())) {
			errors.add("Please enter city");
		}
		
		if (!StringUtils.hasLength(addressDto.getZipCode())) {
			errors.add("Please enter zip code");
		}
		
		if (!StringUtils.hasLength(addressDto.getCountry())) {
			errors.add("Please enter country");
		}
		return errors;
	}
}
