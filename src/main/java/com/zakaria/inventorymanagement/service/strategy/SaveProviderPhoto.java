package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.dto.ProviderDto;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.entity.Provider;
import com.zakaria.inventorymanagement.service.FlickrService;
import com.zakaria.inventorymanagement.service.Impl.ProviderServiceImpl;
import com.zakaria.inventorymanagement.service.ProviderService;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("fournisseurStrategy")
@Slf4j
public class SaveProviderPhoto implements Strategy<ProviderDto> {
	
	private FlickrService flickrService;
	private ProviderService providerService;
	
	@Autowired
	public SaveProviderPhoto(FlickrService flickrService, ProviderService providerService) {
		this.flickrService = flickrService;
		this.providerService = providerService;
	}
	
	@Override
	public ProviderDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException, IOException {
		ProviderDto provider = providerService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if (!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Error while saving picture...", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		provider.setPhoto(urlPhoto);
		return providerService.save(provider);
	}
}