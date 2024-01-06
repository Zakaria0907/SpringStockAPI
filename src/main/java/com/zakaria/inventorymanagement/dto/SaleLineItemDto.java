package com.zakaria.inventorymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleLineItemDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private SaleDto sale;
	
	private BigDecimal quantity;
	
	private BigDecimal unitPrice;
}
