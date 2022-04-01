package com.mk.api.service;


import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.api.dto.request.CommunityModifyRequestDto;
import com.mk.api.dto.response.CommunityGetListResponseDto;
import com.mk.api.dto.response.CommunityGetResponseDto;
import com.mk.db.entity.Community;

public interface CommunityService {

	Community registerCommunity(String accessToken, CommunityRegisterRequestDto communityRegisterRequestDto);
	
	CommunityGetResponseDto getCommunity(String communityId);
	
	CommunityGetListResponseDto getCommunityList(int pageNumber);

	CommunityGetListResponseDto getMyCommunityList(String accessToken);

	CommunityGetListResponseDto getCommunityList();
	
	Community modifyCommunity(CommunityModifyRequestDto communityUpdateRequestDto);

	Community deleteCommunity(String communityId);
	

}
