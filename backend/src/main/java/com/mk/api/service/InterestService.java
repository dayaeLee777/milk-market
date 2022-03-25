package com.mk.api.service;

import com.mk.api.dto.response.InterestGetListResponseDto;
import com.mk.db.entity.Interest;
import org.springframework.data.domain.Pageable;

public interface InterestService {
    Interest registerInterest(String accessToken, String pid);

    InterestGetListResponseDto getItemList(String accessToken);
    InterestGetListResponseDto getItemLists(String accessToken, Pageable pageable);
    Interest deleteInterest(String itemId);
}
