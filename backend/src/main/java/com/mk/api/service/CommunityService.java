package com.mk.api.service;

import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.db.entity.Community;

public interface CommunityService {

	Community registerCommunity(CommunityRegisterRequestDto communityRegisterRequestDto);
	
}
