package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.RoleDto;
import com.zakaria.inventorymanagement.entity.Role;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements Mapper<Role, RoleDto> {
	private ModelMapper modelMapper;
	
	public RoleMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public RoleDto mapTo(Role role) {
		return modelMapper.map(role, RoleDto.class);
	}
	
	@Override
	public Role mapFrom(RoleDto roleDto) {
		return modelMapper.map(roleDto, Role.class);
	}
}