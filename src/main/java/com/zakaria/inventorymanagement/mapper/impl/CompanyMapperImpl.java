package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import com.zakaria.inventorymanagement.entity.Company;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapperImpl implements Mapper<Company, CompanyDto> {
	private ModelMapper modelMapper;
	
	public CompanyMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public CompanyDto mapTo(Company company) {
		return modelMapper.map(company, CompanyDto.class);
	}
	
	@Override
	public Company mapFrom(CompanyDto companyDto) {
		return modelMapper.map(companyDto, Company.class);
	}
}
