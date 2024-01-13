package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import com.zakaria.inventorymanagement.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/company")
@RequiredArgsConstructor
public class CompanyController  {

	private CompanyService companyService;

	@Autowired
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@PostMapping(path = "/save")
	public CompanyDto save(@RequestBody CompanyDto dto) {
		return companyService.save(dto);
	}
	
	@GetMapping(path = "/id/{company-id}")
	public CompanyDto findById(@PathVariable("company-id") Integer id) {
		return companyService.findById(id);
	}
	
	@GetMapping(path = "/all")
	public List<CompanyDto> findAll() {
		return companyService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{company-id}")
	public void delete(@PathVariable("company-id") Integer id) {
		companyService.delete(id);
	}
}
