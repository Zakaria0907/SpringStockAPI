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
public class ProviderOrderDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String code;
	
	private OrderStatus orderStatus;
	
	private Instant orderDate;
	
	private ProviderDto provider;
	
	private List<ProviderLineOrderDto> providerLineOrders;
	
	public boolean isOrderDelivered() {
		return OrderStatus.DELIVERED.equals(this.orderStatus);
	}
}
