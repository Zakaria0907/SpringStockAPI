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
public class CompanyDto {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private AddressDto address;
	
	private String fiscalCode;
	
	private String photo;
	
	private String email;
	
	private String phoneNumber;
	
	private String website;
	
	private List<CompanyUserDto> companyUsers;
}
