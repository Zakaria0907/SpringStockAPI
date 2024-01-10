package com.zakaria.inventorymanagement.service;

import com.zakaria.inventorymanagement.dto.ClientDto;
import java.util.List;

public interface ClientService {
	
	ClientDto save(ClientDto clientDto);
	
	ClientDto findById(Integer id);
	
	List<ClientDto> findAll();
	
	void delete(Integer id);
	
}

