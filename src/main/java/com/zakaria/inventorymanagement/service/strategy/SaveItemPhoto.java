package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.dto.ItemDto;
import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.zakaria.inventorymanagement.service.Impl.FlickrServiceImpl;
import com.zakaria.inventorymanagement.service.Impl.ItemServiceImpl;
import com.zakaria.inventorymanagement.service.ItemService;
import com.zakaria.inventorymanagement.service.FlickrService;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("articleStrategy")
@Slf4j
public class SaveItemPhoto implements Strategy<ItemDto> {
	
	private FlickrService flickrService;
	private ItemService articleService;
	
	@Autowired
	public SaveItemPhoto(FlickrService flickrService, ItemService articleService) {
		this.flickrService = flickrService;
		this.articleService = articleService;
	}
	
	@Override
	public ItemDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException, IOException {
		ItemDto article = articleService.findById(id);
		String urlPhoto = flickrService.savePhoto(photo, titre);
		if (!StringUtils.hasLength(urlPhoto)) {
			throw new InvalidOperationException("Error while saving picture...", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
		}
		article.setPhoto(urlPhoto);
		return articleService.save(article);
	}
}
