package com.mk.api.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mk.api.dto.request.CommunityRegisterRequestDto;
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

}
