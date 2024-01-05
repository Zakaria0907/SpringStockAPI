package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.ProviderOrderDto;
import com.zakaria.inventorymanagement.entity.ProviderOrder;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProviderOrderMapperImpl implements Mapper<ProviderOrder, ProviderOrderDto> {
	private ModelMapper modelMapper;
	
	public ProviderOrderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ProviderOrderDto mapTo(ProviderOrder providerOrder) {
		return modelMapper.map(providerOrder, ProviderOrderDto.class);
	}
	
	@Override
	public ProviderOrder mapFrom(ProviderOrderDto providerOrderDto) {
		return modelMapper.map(providerOrderDto, ProviderOrder.class);
	}
}