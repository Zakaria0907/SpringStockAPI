package com.zakaria.inventorymanagement.dto;

import com.zakaria.inventorymanagement.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ItemDto {
	
	private Integer id;
	
	private String itemCode;
	
	private String description;
	
	private BigDecimal unitPriceBeforeTax;
	
	private BigDecimal tax;
	
	private BigDecimal unitPriceAfterTax;
	
	private String photo;
	
	private Category category;
	
}
