package com.zakaria.inventorymanagement.service.Impl;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.dto.UpdateUserPasswordDto;
import com.zakaria.inventorymanagement.entity.CompanyUser;
import com.zakaria.inventorymanagement.exception.EntityNotFoundException;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidEntityException;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.mapper.impl.CompanyUserMapperImpl;
import com.zakaria.inventorymanagement.repository.CompanyUserRepository;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import com.zakaria.inventorymanagement.validator.CompanyUserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class CompanyUserServiceImpl implements CompanyUserService {
	
	private CompanyUserRepository companyUserRepository; // Define your repository here

	private CompanyUserMapperImpl companyUserMapper; // Define your mapper here
	private CompanyUserValidator companyUserValidator; // Define your validator here
	
	public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository,  CompanyUserMapperImpl companyUserMapper) {
		this.companyUserRepository = companyUserRepository;
		this.companyUserMapper = companyUserMapper;
	}
	
	@Override
	public CompanyUserDto save(CompanyUserDto companyUserDto) {
		List<String> errors = CompanyUserValidator.validateCompanyUser(companyUserDto);
		if (!errors.isEmpty()) {
			log.error("CompanyUser is not valid {}", companyUserDto);
			throw new InvalidEntityException("The company user is not valid", ErrorCodes.USER_NOT_FOUND, errors);
		}
		
		if (userAlreadyExists(companyUserDto.getEmail())) {
			throw new InvalidEntityException("Another user with the same email already exists", ErrorCodes.USER_ALREADY_EXISTS,
					Collections.singletonList("Another user with the same email already exists in the database"));
		}

		companyUserRepository.save(companyUserMapper.mapFrom(companyUserDto));
		
		return companyUserDto;
	}
	
	@Override
	public CompanyUserDto findById(Integer id) {
		if (id == null) {
			log.error("CompanyUser ID is null");
			return null;
		}
		
		Optional<CompanyUser> companyUserEntityOptional = companyUserRepository.findById(id);
		if (companyUserEntityOptional.isEmpty()) {
			throw new EntityNotFoundException("No CompanyUser with ID = " + id + " was found in the database",
					ErrorCodes.USER_NOT_FOUND);
		}
		
		return companyUserMapper.mapTo(companyUserEntityOptional.get());
	}
	
	
	
	@Override
	public List<CompanyUserDto> findAll() {
		List<CompanyUser> companyUsers = companyUserRepository.findAll();
		return companyUsers.stream()
				.map(companyUserMapper::mapTo)
				.collect(Collectors.toList());
	}
	
	@Override
	public void delete(Integer id) {
		if (id == null) {
			log.error("CompanyUser ID is null");
			return;
		}
		companyUserRepository.deleteById(id);
	}
	
	
	@Override
	public CompanyUserDto findByEmail(String email) {
		return companyUserRepository.findCompanyUserByEmail(email)
				.map(companyUserMapper::mapTo)
				.orElseThrow(() -> new EntityNotFoundException(
						"No company user with email = " + email + " found in the database",
						ErrorCodes.USER_NOT_FOUND)
				);
	}
	
	@Override
	public CompanyUserDto changePassword(UpdateUserPasswordDto updateUserPasswordDto) {
		validate(updateUserPasswordDto);
		Optional<CompanyUser> companyUserOptional = companyUserRepository.findById(updateUserPasswordDto.getId());
		if (companyUserOptional.isEmpty()) {
			log.warn("No company user found with ID " + updateUserPasswordDto.getId());
			throw new EntityNotFoundException("No company user found with ID " + updateUserPasswordDto.getId(), ErrorCodes.USER_NOT_FOUND);
		}
		
		CompanyUser companyUser = companyUserOptional.get();
		
		return companyUserMapper.mapTo(companyUserRepository.save(companyUser));
	}
	
	private void validate(UpdateUserPasswordDto dto) {
		if (dto == null) {
			log.warn("Unable to change the password with a NULL object");
			throw new InvalidOperationException("No information provided to change the password",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (dto.getId() == null) {
			log.warn("Unable to change the password with a NULL ID");
			throw new InvalidOperationException("User ID is null: Unable to change the password",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (!StringUtils.hasLength(dto.getPassword()) || !StringUtils.hasLength(dto.getConfirmPassword())) {
			log.warn("Unable to change the password with a NULL password");
			throw new InvalidOperationException("User password is null: Unable to change the password",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
		if (!dto.getPassword().equals(dto.getConfirmPassword())) {
			log.warn("Unable to change the password with different passwords");
			throw new InvalidOperationException("User passwords do not match: Unable to change the password",
					ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
		}
	}
	
	
	
	private boolean userAlreadyExists(String email) {
		Optional<CompanyUser> user = companyUserRepository.findCompanyUserByEmail(email);
		return user.isPresent();
	}
}
