package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.entity.CompanyUser;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyUserMapperImpl implements Mapper<CompanyUser, CompanyUserDto> {
	private ModelMapper modelMapper;
	
	public CompanyUserMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public CompanyUserDto mapTo(CompanyUser companyUser) {
		return modelMapper.map(companyUser, CompanyUserDto.class);
	}
	
	@Override
	public CompanyUser mapFrom(CompanyUserDto companyUserDto) {
		return modelMapper.map(companyUserDto, CompanyUser.class);
	}
}
