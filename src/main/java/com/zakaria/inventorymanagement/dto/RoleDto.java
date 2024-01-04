package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
	
	private Integer id;
	
	private String name;
	
	private CompanyUserDto companyUser;
}
