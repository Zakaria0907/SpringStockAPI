package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import com.zakaria.inventorymanagement.service.ProviderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Provider", description = "Provider API")
@RestController
@RequestMapping(path = "/v1/provider")
@RequiredArgsConstructor
public class ProviderController {

	private ProviderService providerService;

	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}
	
	@PostMapping(path = "/save")
	public ProviderDto save(@RequestBody ProviderDto dto) {
		return providerService.save(dto);
	}
	
	@GetMapping(path = "/id/{provider-id}")
	public ProviderDto findById(@PathVariable("provider-id")  Integer id) {
		return providerService.findById(id);
	}
	
	@GetMapping(path = "/all")
	public List<ProviderDto> findAll() {
		return providerService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{provider-id}")
	public void delete(@PathVariable("provider-id") Integer id) {
		providerService.delete(id);
	}
}
