package com.mk.api.service;

import com.mk.api.dto.response.InterestGetListResponseDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.db.entity.Interest;

import java.util.List;

public interface InterestService {
    Interest registerInterest(String accessToken, String pid);

    InterestGetListResponseDto getItemList(String accessToken);

    Interest deleteInterest(String itemId);
}
