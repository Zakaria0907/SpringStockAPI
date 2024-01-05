package com.zakaria.inventorymanagement.mappers;

import com.zakaria.inventorymanagement.dto.AddressDto;
import com.zakaria.inventorymanagement.entity.Address;

public interface Mapper<A, B> {
	
	B mapTo(A a);
	
	A mapFrom(B b);
}
