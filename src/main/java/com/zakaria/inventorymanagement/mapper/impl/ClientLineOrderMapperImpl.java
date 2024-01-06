package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.ClientLineOrderDto;
import com.zakaria.inventorymanagement.entity.ClientLineOrder;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientLineOrderMapperImpl implements Mapper<ClientLineOrder, ClientLineOrderDto> {
	private ModelMapper modelMapper;
	
	public ClientLineOrderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ClientLineOrderDto mapTo(ClientLineOrder clientLineOrder) {
		return modelMapper.map(clientLineOrder, ClientLineOrderDto.class);
	}
	
	@Override
	public ClientLineOrder mapFrom(ClientLineOrderDto clientLineOrderDto) {
		return modelMapper.map(clientLineOrderDto, ClientLineOrder.class);
	}
}
