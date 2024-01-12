package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.APP_ROOT;

import com.zakaria.inventorymanagement.dto.SaleDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("sales")
public interface SaleApi {
	
	@PostMapping(APP_ROOT + "/sales/create")
	SaleDto save(@RequestBody SaleDto dto);
	
	@GetMapping(APP_ROOT + "/sales/{idSale}")
	SaleDto findById(@PathVariable("idSale") Integer id);
	
	@GetMapping(APP_ROOT + "/sales/code/{codeSale}")
	SaleDto findByCode(@PathVariable("codeSale") String code);
	
	@GetMapping(APP_ROOT + "/sales/all")
	List<SaleDto> findAll();
	
	@DeleteMapping(APP_ROOT + "/sales/delete/{idSale}")
	void delete(@PathVariable("idSale") Integer id);
}
