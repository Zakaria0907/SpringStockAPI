package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.SaleDto;
import com.zakaria.inventorymanagement.entity.Sale;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SaleMapperImpl implements Mapper<Sale, SaleDto> {
	private ModelMapper modelMapper;
	
	public SaleMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public SaleDto mapTo(Sale sale) {
		return modelMapper.map(sale, SaleDto.class);
	}
	
	@Override
	public Sale mapFrom(SaleDto saleDto) {
		return modelMapper.map(saleDto, Sale.class);
	}
}