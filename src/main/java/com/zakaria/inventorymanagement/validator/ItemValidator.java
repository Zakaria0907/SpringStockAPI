package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import com.zakaria.inventorymanagement.dto.ItemDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemValidator {
	
	public static List<String> validateItem(ItemDto itemDto) {
		List<String> errors = new ArrayList<>();
		
		if (itemDto == null) {
			errors.add("Please enter item code");
			errors.add("Please enter unit price before tax");
			errors.add("Please enter tax rate");
			errors.add("Please enter unit price after tax");
			errors.add("Please enter category");
			return errors;
		}
		
		if (!StringUtils.hasLength(itemDto.getItemCode())) {
			errors.add("Please enter item code");
		}
		
		if (!StringUtils.hasLength(itemDto.getDescription())) {
			errors.add("Please enter item description");
		}
		
		if (itemDto.getUnitPriceBeforeTax() == null) {
			errors.add("Please enter unit price before tax");
		}
		
		if (itemDto.getTax() == null) {
			errors.add("Please enter tax rate");
		}
		
		if (itemDto.getUnitPriceAfterTax() == null) {
			errors.add("Please enter unit price after tax");
		}
		
		if (itemDto.getCategory() == null || itemDto.getCategory().getId() == null) {
			errors.add("Please enter category");
		}
		
		return errors;
	}
}
