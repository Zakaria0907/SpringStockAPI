package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import com.zakaria.inventorymanagement.entity.Provider;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.mapper.impl.ProviderMapperImpl;
import com.zakaria.inventorymanagement.repository.ProviderRepository;
import com.zakaria.inventorymanagement.service.ProviderService;
import com.zakaria.inventorymanagement.validator.ProviderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProviderServiceImpl implements ProviderService {
	
	private final ProviderRepository providerRepository;
	private final ProviderMapperImpl providerMapper;
	
	@Autowired
	public ProviderServiceImpl(ProviderRepository providerRepository, ProviderMapperImpl providerMapper) {
		this.providerRepository = providerRepository;
		this.providerMapper = providerMapper;
	}
	
	@Override
	public ProviderDto save(ProviderDto dto) {
		List<String> errors = ProviderValidator.validateProvider(dto);
		if (!errors.isEmpty()) {
			throw new InvalidEntityException("Provider is not valid", ErrorCodes.PROVIDER_NOT_VALID, errors);
		}
		
		Provider provider = providerRepository.save(providerMapper.mapFrom(dto));
		return providerMapper.mapTo(provider);
	}
	
	@Override
	public ProviderDto findById(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}
		
		Provider provider = providerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("No provider with ID = " + id + " found in the database", ErrorCodes.PROVIDER_NOT_FOUND));
		
		return providerMapper.mapTo(provider);
	}
	
	@Override
	public List<ProviderDto> findAll() {
		List<Provider> providers = providerRepository.findAll();
		return providers.stream()
				.map(providerMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}
		
		providerRepository.deleteById(id);
	}
}
