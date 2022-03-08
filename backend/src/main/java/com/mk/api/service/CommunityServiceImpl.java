package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.api.dto.response.CommunityGetResponseDto;
import com.mk.db.entity.Community;
import com.mk.db.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service("communityService")
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

	private final CommunityRepository communityRepository;
	
	@Transactional
	@Override
	public Community registerCommunity(CommunityRegisterRequestDto communityRegisterRequestDto) {
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		Community community = Community.builder()
				.title(communityRegisterRequestDto.getTitle())
				.content(communityRegisterRequestDto.getContent())
				.regTime(currentDateTime)
				.hit(0)
//				.user(communityRegisterRequestDto.getUserId())
				.build();
		
		return communityRepository.save(community);
	}

	@Override
	public CommunityGetResponseDto getCommunity(String communityId) {

		Community community = communityRepository.findById(communityId).orElse(null);
		
		if(community == null)
			return null;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd KK:mm:ss");
		
		community.plusCommunityHit();
		
		CommunityGetResponseDto communityGetResponseDto = CommunityGetResponseDto.builder()
				.communityId(community.getId())
				.title(community.getTitle())
				.content(community.getContent())
				.hit(community.getHit())
				.regTime(community.getRegTime().format(dateTimeFormatter))
//				.userId(userId)
//				.userNickname(userNickname)
				.build();
		
		communityRepository.save(community);
		
		return communityGetResponseDto;
		
	}

}
