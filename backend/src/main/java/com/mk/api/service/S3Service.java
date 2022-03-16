package com.mk.api.service;


public interface S3Service {
	
//	String uploadProfileImg(String accessToken, MultipartFile multipartFile);

	String createFileName(String fileName);
	
	String getFileExtension(String fileName); 
	
	String getFilePath(String newFileName);
}
