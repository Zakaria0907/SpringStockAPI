package com.zakaria.inventorymanagement.controller;

import com.flickr4java.flickr.FlickrException;
import com.zakaria.inventorymanagement.service.strategy.StrategyPhotoContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Photo", description = "Photo API")
@RestController
@RequestMapping(path = "/api/photo")
@RequiredArgsConstructor
public class PhotoController {

	private StrategyPhotoContext strategyPhotoContext;

	@Autowired
	public PhotoController(StrategyPhotoContext strategyPhotoContext) {
		this.strategyPhotoContext = strategyPhotoContext;
	}
	
	@PostMapping(path = "/save/{context}/{id}/{title}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Object savePhoto(@PathVariable("context") String context,
	                        @PathVariable("id") Integer id,
	                        @RequestParam("file") MultipartFile photo,
	                        @PathVariable("title") String title) throws IOException, FlickrException {
		return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), title);
	}
}
