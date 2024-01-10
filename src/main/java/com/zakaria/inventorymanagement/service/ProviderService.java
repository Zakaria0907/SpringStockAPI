package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import java.util.List;

public interface ProviderService {
	
	ProviderDto save(ProviderDto providerDto);
	
	ProviderDto findById(Integer id);
	
	List<ProviderDto> findAll();
	
	void delete(Integer id);
	
}
