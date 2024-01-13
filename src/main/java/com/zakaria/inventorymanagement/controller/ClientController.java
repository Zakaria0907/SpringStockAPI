package com.zakaria.inventorymanagement.controller;

import com.zakaria.inventorymanagement.dto.ClientDto;
import com.zakaria.inventorymanagement.service.ClientService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/client")
@RequiredArgsConstructor
public class ClientController  {

	private ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostMapping(path = "/save")
	public ClientDto save(@RequestBody ClientDto dto) {
		return clientService.save(dto);
	}
	
	@GetMapping(path = "/id/{client-id}")
	public ClientDto findById(@PathVariable("client-id") Integer id) {
		return clientService.findById(id);
	}
	
	@GetMapping(path = "/all")
	public List<ClientDto> findAll() {
		return clientService.findAll();
	}
	
	@DeleteMapping(path = "/delete/{client-id}")
	public void delete(@PathVariable("client-id") Integer id) {
		clientService.delete(id);
	}
}
