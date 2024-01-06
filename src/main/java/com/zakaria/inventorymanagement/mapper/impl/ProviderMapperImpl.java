package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import com.zakaria.inventorymanagement.entity.Provider;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProviderMapperImpl implements Mapper<Provider, ProviderDto> {
	private ModelMapper modelMapper;
	
	public ProviderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ProviderDto mapTo(Provider provider) {
		return modelMapper.map(provider, ProviderDto.class);
	}
	
	@Override
	public Provider mapFrom(ProviderDto providerDto) {
		return modelMapper.map(providerDto, Provider.class);
	}
}
