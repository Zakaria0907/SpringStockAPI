package com.zakaria.inventorymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String code;
	
	private Instant saleDate;
	
	private String comment;
	
	private List<SaleLineItemDto> saleLineItems;
}
