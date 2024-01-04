package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProviderLineOrderDto {
	
	private Integer id;
	
	private BigDecimal quantity;
	
	private BigDecimal unitPrice;
	
	private ItemDto item;
	
	private ProviderOrderDto providerOrder;
}
