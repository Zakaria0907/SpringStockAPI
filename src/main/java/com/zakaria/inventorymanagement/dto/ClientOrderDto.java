package com.zakaria.inventorymanagement.dto;

import com.zakaria.inventorymanagement.entity.enums.OrderStatus;
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
public class ClientOrderDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String code;
	
	private Instant orderDate;
	
	private ClientDto client;
	
	private OrderStatus orderStatus;
	
	private List<ClientLineOrderDto> clientLineOrders;
}
