package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import com.zakaria.inventorymanagement.dto.AddressDto;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CompanyValidator {
	
	public static List<String> validateCompany(CompanyDto companyDto) {
		List<String> errors = new ArrayList<>();
		if (companyDto == null) {
			errors.add("Please provide the company name");
			errors.add("Please provide the company description");
			errors.add("Please provide the company fiscal code");
			errors.add("Please provide the company email");
			errors.add("Please provide the company phone number");
			errors.addAll(AddressValidator.validateAddress(null));
			return errors;
		}
		
		if (!StringUtils.hasLength(companyDto.getName())) {
			errors.add("Please provide the company name");
		}
		if (!StringUtils.hasLength(companyDto.getDescription())) {
			errors.add("Please provide the company description");
		}
		if (!StringUtils.hasLength(companyDto.getFiscalCode())) {
			errors.add("Please provide the company fiscal code");
		}
		if (!StringUtils.hasLength(companyDto.getEmail())) {
			errors.add("Please provide the company email");
		}
		if (!StringUtils.hasLength(companyDto.getPhoneNumber())) {
			errors.add("Please provide the company phone number");
		}
		errors.addAll(AddressValidator.validateAddress(companyDto.getAddress()));
//		if (!StringUtils.hasLength(dto.getWebsite())) {
//			errors.add("Please provide the company website");
//		}
//		if (dto.getCompanyUsers() == null || dto.getCompanyUsers().isEmpty()) {
//			errors.add("Please provide the company users");
//		}
		
		return errors;
	}
}
