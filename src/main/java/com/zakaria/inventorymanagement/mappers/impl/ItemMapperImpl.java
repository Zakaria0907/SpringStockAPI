package com.zakaria.inventorymanagement.mappers.impl;

import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.entity.Item;
import com.zakaria.inventorymanagement.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements Mapper<Item, ItemDto> {
	private ModelMapper modelMapper;
	
	public ItemMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ItemDto mapTo(Item item) {
		return modelMapper.map(item, ItemDto.class);
	}
	
	@Override
	public Item mapFrom(ItemDto itemDto) {
		return modelMapper.map(itemDto, Item.class);
	}
}
