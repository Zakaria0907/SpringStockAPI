package com.zakaria.inventorymanagement.controller;

import com.flickr4java.flickr.FlickrException;
import com.zakaria.inventorymanagement.service.strategy.StrategyPhotoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/v1/photo")
@RequiredArgsConstructor
public class PhotoController {

	private StrategyPhotoContext strategyPhotoContext;

	@Autowired
	public PhotoController(StrategyPhotoContext strategyPhotoContext) {
		this.strategyPhotoContext = strategyPhotoContext;
	}
	
	@PostMapping(path = "/save")
	public Object savePhoto(@PathVariable("order-id") String context,
	                        @PathVariable("order-id") Integer id,
	                        @PathVariable("order-id") MultipartFile photo,
	                        @PathVariable("order-id") String title) throws IOException, FlickrException {
		return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
	}
}
