//package com.zakaria.inventorymanagement.controller;
//
//import com.zakaria.inventorymanagement.dto.SaleDto;
//import com.zakaria.inventorymanagement.service.SaleService;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SaleController {
//
//	private SaleService saleService;
//
//	@Autowired
//	public SaleController(SaleService saleService) {
//		this.saleService = saleService;
//	}
//
//	@Override
//	public SaleDto save(SaleDto dto) {
//		return saleService.save(dto);
//	}
//
//
//	@Override
//	public SaleDto findById(Integer id) {
//		return saleService.findById(id);
//	}
//
//	@Override
//	public SaleDto findByCode(String code) {
//		return saleService.findByCode(code);
//	}
//
//	@Override
//	public List<SaleDto> findAll() {
//		return saleService.findAll();
//	}
//
//	@Override
//	public void delete(Integer id) {
//		saleService.delete(id);
//	}
//}
