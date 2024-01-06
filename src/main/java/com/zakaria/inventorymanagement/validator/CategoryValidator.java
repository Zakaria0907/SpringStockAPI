package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
	
	public static List<String> validateCategory(CategoryDto categoryDto) {
		List<String> errors = new ArrayList<>();
		
		if (categoryDto == null || !StringUtils.hasLength(categoryDto.getCode())) {
			errors.add("Please enter code");
		}
		return errors;
	}
}
