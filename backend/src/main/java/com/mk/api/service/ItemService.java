package com.mk.api.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.db.entity.Item;

public interface ItemService {

	Item registerItem(String accessToken, List<MultipartFile> multipartFile, ItemRegisterRequestDto itemRegisterRequestDto);

	Item getItemEntity(String itemId);
	ItemGetResponseDto getItem(String itemId);
	
	Item modifyItem(ItemModifyRequestDto itemModifyRequestDto, List<MultipartFile> multipartFile);

	Item deleteItem(String itemId);
	
}

