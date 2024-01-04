package com.zakaria.inventorymanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CompanyUserDto {
	
	private Integer id;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	
	private Instant dateOfBirth;
	
	private String password;
	
	private AddressDto address;
	
	private String photo;
	
	private CompanyDto company;
	
	private List<RoleDto> roles;
}
