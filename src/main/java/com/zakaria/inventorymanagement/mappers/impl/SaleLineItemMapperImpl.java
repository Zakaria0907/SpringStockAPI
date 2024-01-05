package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.SaleLineItemDto;
import com.zakaria.inventorymanagement.entity.SaleLineItem;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SaleLineItemMapperImpl implements Mapper<SaleLineItem, SaleLineItemDto> {
	private ModelMapper modelMapper;
	
	public SaleLineItemMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public SaleLineItemDto mapTo(SaleLineItem saleLineItem) {
		return modelMapper.map(saleLineItem, SaleLineItemDto.class);
	}
	
	@Override
	public SaleLineItem mapFrom(SaleLineItemDto saleLineItemDto) {
		return modelMapper.map(saleLineItemDto, SaleLineItem.class);
	}
}
