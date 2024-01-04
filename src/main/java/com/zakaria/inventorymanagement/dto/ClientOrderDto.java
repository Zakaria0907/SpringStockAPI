package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class ClientOrderDto {
	
	private Integer id;
	
	private String code;
	
	private Instant orderDate;
	
	private ClientDto client;
	
	private List<ClientLineOrderDto> clientLineOrders;
}
