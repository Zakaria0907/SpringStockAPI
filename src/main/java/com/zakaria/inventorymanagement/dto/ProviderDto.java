package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProviderDto {
	
	private Integer id;
	
	private String lastname;
	
	private String firstname;
	
	private AddressDto address;
	
	private String photo;
	
	private String email;
	
	private String phoneNumber;
	
	private List<ProviderOrderDto> providerOrders;
}
