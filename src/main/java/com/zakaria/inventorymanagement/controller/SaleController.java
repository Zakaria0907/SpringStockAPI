package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.SaleDto;
import com.zakaria.inventorymanagement.service.SaleService;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Sale", description = "Sale API")
@RestController
@RequestMapping(path = "/v1/sale")
@RequiredArgsConstructor
public class SaleController {

	private SaleService saleService;

	@Autowired
	public SaleController(SaleService saleService) {
		this.saleService = saleService;
	}
	
	@PostMapping(path = "/save")
	public SaleDto save(@RequestBody SaleDto dto) {
		return saleService.save(dto);
	}
	
	
	@GetMapping(path = "/id/{sale-id}")
	public SaleDto findById(@PathVariable("sale-id") Integer id) {
		return saleService.findById(id);
	}
	
	@GetMapping(path = "/code/{order-code}")
	public SaleDto findByCode(@PathVariable("order-code") String code) {
		return saleService.findByCode(code);
	}
	
	@GetMapping(path = "/all")
	public List<SaleDto> findAll() {
		return saleService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{sale-id}")
	public void delete(@PathVariable("sale-id") Integer id) {
		saleService.delete(id);
	}
}
