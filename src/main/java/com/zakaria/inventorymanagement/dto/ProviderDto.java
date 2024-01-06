package com.zakaria.inventorymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String lastname;
	
	private String firstname;
	
	private AddressDto address;
	
	private String photo;
	
	private String email;
	
	private String phoneNumber;
	
	private List<ProviderOrderDto> providerOrders;
}
