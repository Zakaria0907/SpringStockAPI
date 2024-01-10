package com.zakaria.inventorymanagement.service;

import java.io.IOException;
import java.io.InputStream;

public interface FlickrService {
	
	String savePhoto(InputStream photo, String title) throws IOException;
}
