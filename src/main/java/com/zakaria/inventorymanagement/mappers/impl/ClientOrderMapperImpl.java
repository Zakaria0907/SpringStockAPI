package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.ClientOrderDto;
import com.zakaria.inventorymanagement.entity.ClientOrder;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientOrderMapperImpl implements Mapper<ClientOrder, ClientOrderDto> {
	private ModelMapper modelMapper;
	
	public ClientOrderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ClientOrderDto mapTo(ClientOrder clientOrder) {
		return modelMapper.map(clientOrder, ClientOrderDto.class);
	}
	
	@Override
	public ClientOrder mapFrom(ClientOrderDto clientOrderDto) {
		return modelMapper.map(clientOrderDto, ClientOrder.class);
	}
}
