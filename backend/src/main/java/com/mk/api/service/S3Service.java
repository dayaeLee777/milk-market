package com.mk.api.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	
	Map<String, String> uploadFile(List<MultipartFile> multipartFile);
	
	String createFileName(String fileName);
	
	String getFileExtension(String fileName); 
	
	String getFilePath(String newFileName);
	
	void deleteFile(String newFileName);
	
}
