package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.dto.ClientDto;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.entity.Client;
import com.zakaria.inventorymanagement.service.ClientService;
import com.zakaria.inventorymanagement.service.FlickrService;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {
	
	private FlickrService flickrService;
	private ClientService clientService;
	
	@Autowired
	public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
		this.flickrService = flickrService;
		this.clientService = clientService;
	}
	
	@Override
	public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException, IOException {
		ClientDto client = clientService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if (!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Error while saving picture...", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		client.setPhoto(urlPhoto);
		return clientService.save(client);
	}
}

