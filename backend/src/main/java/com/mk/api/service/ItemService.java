package com.mk.api.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.db.entity.Item;

public interface ItemService {

	Item registerItem(String accessToken, List<MultipartFile> multipartFile, ItemRegisterRequestDto itemRegisterRequestDto);
	
}
