package com.zakaria.inventorymanagement.mapper.impl;


import com.zakaria.inventorymanagement.dto.AddressDto;
import com.zakaria.inventorymanagement.entity.Address;
import com.zakaria.inventorymanagement.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements Mapper<Address, AddressDto> {
	private ModelMapper modelMapper;
	
	public AddressMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public AddressDto mapTo(Address address) {
		return modelMapper.map(address, AddressDto.class);
	}
	
	@Override
	public Address mapFrom(AddressDto addressDto) {
		return modelMapper.map(addressDto, Address.class);
	}
}
