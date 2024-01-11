package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.UpdateUserPasswordDto;
import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyUserService {
	
	CompanyUserDto save(CompanyUserDto companyUserDto);
	
	CompanyUserDto findById(Integer id);
	
	List<CompanyUserDto> findAll();
	
	void delete(Integer id);
	
	CompanyUserDto findByEmail(String email);
	
	CompanyUserDto changePassword(UpdateUserPasswordDto updateUserPasswordDto);
	
}
