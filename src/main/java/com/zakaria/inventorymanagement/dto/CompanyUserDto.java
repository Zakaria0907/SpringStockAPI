package com.zakaria.inventorymanagement.dto;

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
public class CompanyUserDto {
	
	private Integer companyId;
	
	private Integer id;
	
	private String lastname;
	
	private String firstname;
	
	private String email;
	
	private Instant dateOfBirth;
	
	private String password;
	
	private AddressDto address;
	
	private String photo;
	
	private CompanyDto company;
	
	private List<RoleDto> roles;
}
