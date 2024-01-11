package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.dto.RoleDto;
import com.zakaria.inventorymanagement.entity.Company;
import com.zakaria.inventorymanagement.entity.CompanyUser;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.mapper.impl.CompanyMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.CompanyUserMapperImpl;
import com.zakaria.inventorymanagement.mapper.impl.RoleMapperImpl;
import com.zakaria.inventorymanagement.repository.CompanyRepository;
import com.zakaria.inventorymanagement.repository.RoleRepository;
import com.zakaria.inventorymanagement.service.CompanyService;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import com.zakaria.inventorymanagement.validator.CompanyValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
	
	private final CompanyRepository companyRepository;
	private final CompanyUserService companyUserService;
	private final RoleRepository roleRepository;
	private final CompanyValidator companyValidator;
	private final CompanyMapperImpl companyMapper;
	private final CompanyUserMapperImpl companyUserMapper;
	private final RoleMapperImpl roleMapper;
	
	
	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository,
	                          CompanyUserService companyUserService,
	                          RoleRepository roleRepository,
	                          CompanyValidator companyValidator,
	                          CompanyMapperImpl companyMapper,
	                          CompanyUserMapperImpl companyUserMapper,
	                          RoleMapperImpl roleMapper) {
		this.companyRepository = companyRepository;
		this.companyUserService = companyUserService;
		this.roleRepository = roleRepository;
		this.companyValidator = companyValidator;
		this.companyMapper = companyMapper;
		this.companyUserMapper = companyUserMapper;
		this.roleMapper = roleMapper;
	}
	
	@Override
	public CompanyDto save(CompanyDto companyDto) {
		List<String> errors = companyValidator.validateCompany(companyDto);
		if (!errors.isEmpty()) {
			log.error("Company is not valid {}", companyDto);
			throw new InvalidEntityException("The company is not valid", ErrorCodes.COMPANY_NOT_VALID, errors);
		}
		
		Company savedCompanyEntity = companyRepository.save(companyMapper.mapFrom(companyDto));
		CompanyDto savedCompany = companyMapper.mapTo(savedCompanyEntity);
		
		CompanyUserDto companyUser = fromCompany(savedCompany);
		CompanyUserDto savedUser = companyUserService.save(companyUser);
		
		RoleDto roleDto = RoleDto.builder()
				.companyUser(savedUser)
				.name("ADMIN")
				.build();
		roleRepository.save(roleMapper.mapFrom(roleDto));
		
		return savedCompany;
	}
	
	private CompanyUserDto fromCompany(CompanyDto dto) {
		return CompanyUserDto.builder()
				.address(dto.getAddress())
				.firstname(dto.getName())
				.email(dto.getEmail())
				.password(generateRandomPassword())
				.company(dto)
				.dateOfBirth(Instant.now())
				.photo(dto.getPhoto())
				.build();
	}
	
	private String generateRandomPassword() {
		return "som3R@nd0mP@$$word";
	}
	
	
	@Override
	public CompanyDto findById(Integer id) {
		if (id == null) {
			log.error("Company ID is null");
			return null;
		}
		return companyRepository.findById(id)
				.map(companyMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No company with ID = " + id + " was found in the database",
						ErrorCodes.COMPANY_NOT_FOUND)
				);
	}
	
	@Override
	public List<CompanyDto> findAll() {
		return companyRepository.findAll().stream()
				.map(companyMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("Company ID is null");
			return;
		}
		companyRepository.deleteById(id);
	}
	
}
