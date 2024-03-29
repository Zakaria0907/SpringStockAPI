package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.dto.UpdateUserPasswordDto;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Company User", description = "Company User API")
@RestController
@RequestMapping(path = "/api/user/")
@RequiredArgsConstructor
public class CompanyUserController  {

	private CompanyUserService companyUserService;

	@Autowired
	public CompanyUserController(CompanyUserService companyUserService) {
		this.companyUserService = companyUserService;
	}
	
	@PostMapping(path = "/save")
	public CompanyUserDto save(@RequestBody CompanyUserDto dto) {
		return companyUserService.save(dto);
	}
	
	@PostMapping(path = "/update_password")
	public CompanyUserDto changePassword(@RequestBody UpdateUserPasswordDto dto) {
		return companyUserService.changePassword(dto);
	}
	
	@GetMapping(path = "/id/{user-id}")
	public CompanyUserDto findById(@PathVariable("user-id") Integer userId) {
		return companyUserService.findById(userId);
	}
	
	@GetMapping(path = "/email/{email}")
	public CompanyUserDto findByEmail(@PathVariable("email") String email) {
		return companyUserService.findByEmail(email);
	}
	
	@GetMapping(path = "/all")
	public List<CompanyUserDto> findAll() {
		return companyUserService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{user-id}")
	public void delete(@PathVariable("user-id") Integer userId) {
		companyUserService.delete(userId);
	}
}
