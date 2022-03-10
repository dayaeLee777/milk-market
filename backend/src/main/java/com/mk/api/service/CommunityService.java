package com.mk.api.service;


import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.api.dto.request.CommunityUpdateRequestDto;
import com.mk.api.dto.response.CommunityGetListResponseDto;
import com.mk.api.dto.response.CommunityGetResponseDto;
import com.mk.db.entity.Community;

public interface CommunityService {

	Community registerCommunity(CommunityRegisterRequestDto communityRegisterRequestDto);
	
	CommunityGetResponseDto getCommunity(String communityId);
	
	CommunityGetListResponseDto getCommunityList(int pageNumber);
	
	Community modifyCommunity(CommunityUpdateRequestDto communityUpdateRequestDto);

	Community deleteCommunity(String communityId);
	

}
