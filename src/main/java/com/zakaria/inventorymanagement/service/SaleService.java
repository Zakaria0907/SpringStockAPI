package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.SaleDto;
import java.util.List;

public interface SaleService {
	
	SaleDto save(SaleDto saleDto);
	
	SaleDto findById(Integer id);
	
	SaleDto findByCode(String code);
	
	List<SaleDto> findAll();
	
	void delete(Integer id);
	
}
