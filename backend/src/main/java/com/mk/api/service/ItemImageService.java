package com.mk.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mk.db.entity.Item;

public interface ItemImageService {
	
	void uploadItemImages(Item item, List<MultipartFile> multipartFile);
	
	void deleteItemImages(Item item);
	
	String getImagePath(String filename);

}
