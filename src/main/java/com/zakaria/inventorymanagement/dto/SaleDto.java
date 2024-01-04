package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SaleDto {
	
	private Integer id;
	
	private String code;
	
	private Instant saleDate;
	
	private String comment;
}
