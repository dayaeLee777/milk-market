package com.mk.api.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mk.api.dto.response.HotItemGetResponseDto;
import com.mk.api.dto.response.InterestGetListResponseDto;
import com.mk.db.entity.Interest;

public interface InterestService {
    Interest registerInterest(String accessToken, String pid);

    InterestGetListResponseDto getItemList(String accessToken);
    InterestGetListResponseDto getItemLists(String accessToken, Pageable pageable);
    Interest deleteInterest(String itemId);
    
    List<HotItemGetResponseDto> getHotItem();
    
}
