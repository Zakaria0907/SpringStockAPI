package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ProviderOrderDto {
	
	private Integer id;
	
	private String code;
	
	private Instant orderDate;
	
	private ProviderDto provider;
	
	private List<ProviderLineOrderDto> providerLineOrders;
}
