package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ComapanyUserValidator {
	
	public static List<String> validateCompanyUser(CompanyUserDto companyUserDto) {
		List<String> errors = new ArrayList<>();
		
		if (companyUserDto == null) {
			errors.add("Please enter lastname");
			errors.add("Please enter firstname");
			errors.add("Please enter password");
			errors.add("Please enter address");
			return errors;
			
		}
		
		if (!StringUtils.hasLength(companyUserDto.getLastname())) {
			errors.add("Please enter lastname");
		}
		
		if (!StringUtils.hasLength(companyUserDto.getFirstname())) {
			errors.add("Please enter firstname");
		}
		
		if (!StringUtils.hasLength(companyUserDto.getPassword())) {
			errors.add("Please enter password");
		}
		
		if (companyUserDto.getDateOfBirth() == null) {
			errors.add("Please enter date of birth");
		}
		
		if (companyUserDto.getAddress() == null) {
			errors.add("Please enter address");
		} else {
			if (!StringUtils.hasLength(companyUserDto.getAddress().getAddress1())) {
				errors.add("field address1 is required");
			}
			
			if (!StringUtils.hasLength(companyUserDto.getAddress().getCity())) {
				errors.add("field city is required");
			}
			
			if (!StringUtils.hasLength(companyUserDto.getAddress().getZipCode())) {
				errors.add("field zip code is required");
			}
			
			if (!StringUtils.hasLength(companyUserDto.getAddress().getCountry())) {
				errors.add("field country is required");
			}
			
			return errors;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return errors;
	}
}
