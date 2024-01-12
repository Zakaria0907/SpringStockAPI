package com.zakaria.inventorymanagement.service.strategy;

import com.zakaria.inventorymanagement.exception.ErrorCodes;
import com.zakaria.inventorymanagement.exception.InvalidOperationException;
import com.flickr4java.flickr.FlickrException;

import java.io.IOException;
import java.io.InputStream;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyPhotoContext {
	
	private BeanFactory beanFactory;
	private Strategy strategy;
	
	@Setter
	private String context;
	
	@Autowired
	public StrategyPhotoContext(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public Object savePhoto(String context, Integer id, InputStream photo, String title) throws FlickrException, IOException {
		determinContext(context);
		return strategy.savePhoto(id, photo, title);
	}
	
	private void determinContext(String context) {
		final String beanName = context + "Strategy";
		switch (context) {
			case "article":
				strategy = beanFactory.getBean(beanName, SaveItemPhoto.class);
				break;
			case "client" :
				strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
				break;
			case "fournisseur" :
				strategy = beanFactory.getBean(beanName, SaveProviderPhoto.class);
				break;
			case "entreprise" :
				strategy = beanFactory.getBean(beanName, SaveCompanyPhoto.class);
				break;
			case "utilisateur" :
				strategy = beanFactory.getBean(beanName, SaveCompanyUserPhoto.class);
				break;
			default: throw new InvalidOperationException("Unknown context for saving photo...", ErrorCodes.UNKNOWN_CONTEXT);
		}
	}
	
	
}
