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
public class ClientLineOrderDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private BigDecimal quantity;
	
	private BigDecimal unitPrice;
	
	private ItemDto item;
	
	private ClientOrderDto clientOrder;
}
