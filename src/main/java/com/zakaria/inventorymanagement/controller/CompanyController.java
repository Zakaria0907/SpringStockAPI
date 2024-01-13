//package com.zakaria.inventorymanagement.controller;
//
//import com.zakaria.inventorymanagement.dto.CompanyDto;
//import com.zakaria.inventorymanagement.service.CompanyService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "/v1/category")
//@RequiredArgsConstructor
//public class CompanyController  {
//
//	private CompanyService companyService;
//
//	@Autowired
//	public CompanyController(CompanyService companyService) {
//		this.companyService = companyService;
//	}
//
//	@Override
//	public CompanyDto save(CompanyDto dto) {
//		return companyService.save(dto);
//	}
//
//	@Override
//	public CompanyDto findById(Integer id) {
//		return companyService.findById(id);
//	}
//
//	@Override
//	public List<CompanyDto> findAll() {
//		return companyService.findAll();
//	}
//
//	@Override
//	public void delete(Integer id) {
//		companyService.delete(id);
//	}
//}
