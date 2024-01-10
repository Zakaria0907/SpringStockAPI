package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import java.util.List;

public interface CompanyService {
	
	CompanyDto save(CompanyDto companyDto);
	
	CompanyDto findById(Integer id);
	
	List<CompanyDto> findAll();
	
	void delete(Integer id);
	
}
