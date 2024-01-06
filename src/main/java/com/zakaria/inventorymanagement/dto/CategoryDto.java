package com.zakaria.inventorymanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
	private Integer companyId;
	
	private Integer id;
	
	private String code;
	
	private String description;
	
	@JsonIgnore
	private List<ItemDto> items;
	
}
