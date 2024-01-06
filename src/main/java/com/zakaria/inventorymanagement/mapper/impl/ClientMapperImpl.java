package com.zakaria.inventorymanagement.mapper.impl;

import com.zakaria.inventorymanagement.dto.ClientDto;
import com.zakaria.inventorymanagement.entity.Client;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientMapperImpl implements Mapper<Client, ClientDto> {
	private ModelMapper modelMapper;
	
	public ClientMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ClientDto mapTo(Client client) {
		return modelMapper.map(client, ClientDto.class);
	}
	
	@Override
	public Client mapFrom(ClientDto clientDto) {
		return modelMapper.map(clientDto, Client.class);
	}
}
