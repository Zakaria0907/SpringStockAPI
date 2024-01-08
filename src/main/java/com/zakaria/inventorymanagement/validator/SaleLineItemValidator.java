package com.zakaria.inventorymanagement.validator;

import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaleLineItemValidator {
	
	public static List<String> validateSaleLineItem(SaleLineItemDto saleLineItemDto) {
		List<String> errors = new ArrayList<>();
		if (saleLineItemDto == null) {
			errors.add("Please provide the sale line item details");
			return errors;
		}
		
		if (saleLineItemDto.getCompanyId() == null) {
			errors.add("Please provide the company ID");
		}
		if (saleLineItemDto.getId() == null) {
			errors.add("Please provide the sale line item ID");
		}
		if (saleLineItemDto.getSale() == null || saleLineItemDto.getSale().getId() == null) {
			errors.add("Please provide the sale details");
		}
		if (saleLineItemDto.getQuantity() == null || saleLineItemDto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please provide a valid quantity");
		}
		if (saleLineItemDto.getUnitPrice() == null || saleLineItemDto.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
			errors.add("Please provide a valid unit price");
		}
		
		return errors;
	}
}
