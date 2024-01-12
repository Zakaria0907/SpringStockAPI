package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.dto.CompanyUserDto;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.service.FlickrService;
import com.zakaria.inventorymanagement.service.CompanyUserService;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;

import com.zakaria.inventorymanagement.service.Impl.CompanyUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("utilisateurStrategy")
@Slf4j
public class SaveCompanyUserPhoto implements Strategy<CompanyUserDto> {
	
	private FlickrService flickrService;
	private CompanyUserService companyUserService;
	
	@Autowired
	public SaveCompanyUserPhoto(FlickrService flickrService, CompanyUserService companyUserServiceImpl) {
		this.flickrService = flickrService;
		this.companyUserService = companyUserServiceImpl;
	}
	
	@Override
	public CompanyUserDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException, IOException {
		CompanyUserDto companyUser = companyUserService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if (!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Error while saving picture...", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		companyUser.setPhoto(urlPhoto);
		return companyUserService.save(companyUser);
	}
}
