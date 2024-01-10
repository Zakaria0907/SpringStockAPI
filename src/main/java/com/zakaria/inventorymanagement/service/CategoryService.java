package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.CategoryDto;
import java.util.List;

public interface CategoryService {
	
	CategoryDto save(CategoryDto categoryDto);
	
	CategoryDto findById(Integer id);
	
	CategoryDto findByCode(String code);
	
	List<CategoryDto> findAll();
	
	void delete(Integer id);
	
}
