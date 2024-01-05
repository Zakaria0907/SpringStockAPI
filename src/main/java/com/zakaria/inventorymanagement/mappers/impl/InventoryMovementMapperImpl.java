package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.InventoryMovementDto;
import com.zakaria.inventorymanagement.entity.InventoryMovement;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InventoryMovementMapperImpl implements Mapper<InventoryMovement, InventoryMovementDto> {
	private ModelMapper modelMapper;
	
	public InventoryMovementMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public InventoryMovementDto mapTo(InventoryMovement inventoryMovement) {
		return modelMapper.map(inventoryMovement, InventoryMovementDto.class);
	}
	
	@Override
	public InventoryMovement mapFrom(InventoryMovementDto inventoryMovementDto) {
		return modelMapper.map(inventoryMovementDto, InventoryMovement.class);
	}
}
