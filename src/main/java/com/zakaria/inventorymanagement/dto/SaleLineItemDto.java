package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SaleLineItemDto {
	
	private Integer id;
	
	private SaleDto sale;
	
	private BigDecimal quantity;
	
	private BigDecimal unitPrice;
}
