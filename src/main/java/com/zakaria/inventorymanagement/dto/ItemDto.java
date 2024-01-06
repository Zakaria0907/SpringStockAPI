package com.zakaria.inventorymanagement.dto;

import com.zakaria.inventorymanagement.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String itemCode;
	
	private String description;
	
	private BigDecimal unitPriceBeforeTax;
	
	private BigDecimal tax;
	
	private BigDecimal unitPriceAfterTax;
	
	private String photo;
	
	private Category category;
	
}
