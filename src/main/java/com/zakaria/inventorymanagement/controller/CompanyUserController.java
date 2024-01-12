package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.CompanyUserApi;
import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.dto.UpdateUserPasswordDto;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import com.zakaria.inventorymanagement.service.Impl.CompanyServiceImpl;
import com.zakaria.inventorymanagement.service.Impl.CompanyUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyUserController implements CompanyUserApi {
	
	private CompanyUserService companyUserService;
	
	@Autowired
	public CompanyUserController(CompanyUserService companyUserService) {
		this.companyUserService = companyUserService;
	}
	
	@Override
	public CompanyUserDto save(CompanyUserDto dto) {
		return companyUserService.save(dto);
	}
	
	@Override
	public CompanyUserDto changePassword(UpdateUserPasswordDto dto) {
		return companyUserService.changePassword(dto);
	}
	
	@Override
	public CompanyUserDto findById(Integer userId) {
		return companyUserService.findById(userId);
	}
	
	@Override
	public CompanyUserDto findByEmail(String email) {
		return companyUserService.findByEmail(email);
	}
	
	@Override
	public List<CompanyUserDto> findAll() {
		return companyUserService.findAll();
	}
	
	@Override
	public void delete(Integer userId) {
		companyUserService.delete(userId);
	}
}
