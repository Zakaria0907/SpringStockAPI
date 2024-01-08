package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.SaleDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

public class SaleValidator {
	
	public static List<String> validateSale(SaleDto saleDto) {
		List<String> errors = new ArrayList<>();
		if (saleDto == null) {
			errors.add("Please provide the sale code");
			errors.add("Please provide the sale date");
			return errors;
		}
		
		if (!StringUtils.hasLength(saleDto.getCode())) {
			errors.add("Please provide the sale code");
		}
		if (saleDto.getSaleDate() == null) {
			errors.add("Please provide the sale date");
		}
		
		return errors;
	}
}
