package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.ProviderLineOrderDto;
import com.zakaria.inventorymanagement.entity.ProviderLineOrder;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProviderLineOrderMapperImpl implements Mapper<ProviderLineOrder, ProviderLineOrderDto> {
	private ModelMapper modelMapper;
	
	public ProviderLineOrderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ProviderLineOrderDto mapTo(ProviderLineOrder providerLineOrder) {
		return modelMapper.map(providerLineOrder, ProviderLineOrderDto.class);
	}
	
	@Override
	public ProviderLineOrder mapFrom(ProviderLineOrderDto providerLineOrderDto) {
		return modelMapper.map(providerLineOrderDto, ProviderLineOrder.class);
	}
}
