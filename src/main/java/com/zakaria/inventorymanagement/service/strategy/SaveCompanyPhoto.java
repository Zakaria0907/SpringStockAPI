package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.dto.CompanyDto;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.service.CompanyService;
import com.zakaria.inventorymanagement.service.FlickrService;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;

import com.zakaria.inventorymanagement.service.Impl.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("entrepriseStrategy")
@Slf4j
public class SaveCompanyPhoto implements Strategy<CompanyDto> {
	
	private FlickrService flickrService;
	private CompanyService entrepriseService;
	
	@Autowired
	public SaveCompanyPhoto(FlickrService flickrService, CompanyService entrepriseService) {
		this.flickrService = flickrService;
		this.entrepriseService = entrepriseService;
	}
	
	@Override
	public CompanyDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException, IOException {
		CompanyDto company = entrepriseService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if (!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Error while saving picture...", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		company.setPhoto(urlPhoto);
		return entrepriseService.save(company);
	}
}
