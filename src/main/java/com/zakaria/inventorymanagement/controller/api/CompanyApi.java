package com.zakaria.inventorymanagement.controller.api;

import static com.zakaria.inventorymanagement.utils.Constant.COMPANY_ENDPOINT;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api("companies")
public interface CompanyApi {
	
	@PostMapping(COMPANY_ENDPOINT + "/create")
	CompanyDto save(@RequestBody CompanyDto dto);
	
	@GetMapping(COMPANY_ENDPOINT + "/{idCompany}")
	CompanyDto findById(@PathVariable("idCompany") Integer id);
	
	@GetMapping(COMPANY_ENDPOINT + "/all")
	List<CompanyDto> findAll();
	
	@DeleteMapping(COMPANY_ENDPOINT + "/delete/{idCompany}")
	void delete(@PathVariable("idCompany") Integer id);
	
}
