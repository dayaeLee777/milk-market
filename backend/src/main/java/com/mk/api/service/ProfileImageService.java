package com.mk.api.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
	
	void uploadProfileImg(String accessToken, List<MultipartFile> multipartFile);

}
