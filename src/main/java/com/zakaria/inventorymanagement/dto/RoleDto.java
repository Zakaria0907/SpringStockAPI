package com.zakaria.inventorymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
	
	private Integer id;
	
	private String name;
	
	private CompanyUserDto companyUser;
}
