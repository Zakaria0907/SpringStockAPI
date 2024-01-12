package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.controller.api.ProviderApi;
import com.zakaria.inventorymanagement.dto.ProviderDto;
import com.zakaria.inventorymanagement.service.Impl.ProviderServiceImpl;
import com.zakaria.inventorymanagement.service.ProviderService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderController implements ProviderApi {
	
	private ProviderService providerService;
	
	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	@Override
	public ProviderDto save(ProviderDto dto) {
		return providerService.save(dto);
	}
	
	@Override
	public ProviderDto findById(Integer id) {
		return providerService.findById(id);
	}
	
	@Override
	public List<ProviderDto> findAll() {
		return providerService.findAll();
	}
	
	@Override
	public void delete(Integer id) {
		providerService.delete(id);
	}
}
