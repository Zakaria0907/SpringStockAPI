package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.ClientDto;
import com.zakaria.inventorymanagement.entity.ClientOrder;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.ClientMapperImpl;
import com.zakaria.inventorymanagement.repository.ClientRepository;
import com.zakaria.inventorymanagement.repository.ClientOrderRepository;
import com.zakaria.inventorymanagement.service.ClientService;
import com.zakaria.inventorymanagement.validator.ClientValidator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
	
	private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
	
	private ClientRepository clientRepository;
	private ClientOrderRepository clientOrderRepository;
	
	private ClientMapperImpl clientMapper;
	
	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository,
	                         ClientOrderRepository clientOrderRepository,
	                         ClientMapperImpl clientMapper) {
		this.clientRepository = clientRepository;
		this.clientOrderRepository = clientOrderRepository;
		this.clientMapper = clientMapper;
	}
	
	@Override
	public ClientDto save(ClientDto clientDto) {
		List<String> errors = ClientValidator.validateClient(clientDto);
		if (!errors.isEmpty()) {
			log.error("Client is not valid {}", clientDto);
			throw new InvalidEntityException("Invalid client", ErrorCodes.CLIENT_NOT_VALID, errors);
		}
		
		return clientMapper.mapTo(
				clientRepository.save(
						clientMapper.mapFrom(clientDto)
				)
		);
	}
	
	@Override
	public ClientDto findById(Integer id) {
		if (id == null) {
			log.error("Client ID is null");
			return null;
		}
		
		return clientRepository.findById(id).map(clientMapper::mapTo).orElseThrow(() ->
				new EntityNotFoundException(
						"No client with ID = " + id + " found in DB",
						ErrorCodes.CLIENT_NOT_FOUND)
		);
	}
	
	@Override
	public List<ClientDto> findAll() {
		return clientRepository.findAll().stream()
				.map(clientMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Client ID is null");
			return;
		}
		List<ClientOrder> clientOrders = clientOrderRepository.findAllByClientId(id);
		if (!clientOrders.isEmpty()) {
			throw new InvalidOperationException("Cannot delete a client who already has client orders",
					ErrorCodes.CLIENT_ALREADY_IN_USE);
		}
		clientRepository.deleteById(id);
	}
	
	// Additional methods can be added here as per your requirements.
}
