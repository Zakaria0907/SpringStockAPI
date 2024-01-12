package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.SUPPLIER_ENDPOINT;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("providers")
public interface ProviderApi {
	
	@PostMapping(SUPPLIER_ENDPOINT + "/create")
	ProviderDto save(@RequestBody ProviderDto dto);
	
	@GetMapping(SUPPLIER_ENDPOINT + "/{idProvider}")
	ProviderDto findById(@PathVariable("idProvider") Integer id);
	
	@GetMapping(SUPPLIER_ENDPOINT + "/all")
	List<ProviderDto> findAll();
	
	@DeleteMapping(SUPPLIER_ENDPOINT + "/delete/{idProvider}")
	void delete(@PathVariable("idProvider") Integer id);
}
