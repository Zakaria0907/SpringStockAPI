package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.COMPANY_USER_ENDPOINT;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.dto.UpdateUserPasswordDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("companyusers")
public interface CompanyUserApi {
	
	@PostMapping(COMPANY_USER_ENDPOINT + "/create")
	CompanyUserDto save(@RequestBody CompanyUserDto dto);
	
	@PostMapping(COMPANY_USER_ENDPOINT + "/update/password")
	CompanyUserDto changePassword(@RequestBody UpdateUserPasswordDto dto);
	
	@GetMapping(COMPANY_USER_ENDPOINT + "/{userId}")
	CompanyUserDto findById(@PathVariable("userId") Integer userId);
	
	@GetMapping(COMPANY_USER_ENDPOINT + "/find/{email}")
	CompanyUserDto findByEmail(@PathVariable("email") String email);
	
	@GetMapping(COMPANY_USER_ENDPOINT + "/all")
	List<CompanyUserDto> findAll();
	
	@DeleteMapping(COMPANY_USER_ENDPOINT + "/delete/{userId}")
	void delete(@PathVariable("userId") Integer userId);
}
