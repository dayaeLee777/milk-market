package com.mk.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mk.api.dto.request.CommunityModifyRequestDto;
import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.api.dto.response.CommunityGetListResponseDto;
import com.mk.api.dto.response.CommunityGetResponseDto;
import com.mk.db.entity.Community;
import com.mk.db.entity.User;
import com.mk.db.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service("communityService")
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

	private final CommunityRepository communityRepository;
	
	private final JwtTokenService jwtTokenService;
	
	@Transactional
	@Override
	public Community registerCommunity(String accessToken, CommunityRegisterRequestDto communityRegisterRequestDto) {
		
		User user = jwtTokenService.convertTokenToUser(accessToken);
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		Community community = Community.builder()
				.title(communityRegisterRequestDto.getTitle())
				.content(communityRegisterRequestDto.getContent())
				.regTime(currentDateTime)
				.hit(0)
				.user(user)
				.build();
		
		return communityRepository.save(community);
	}

	@Override
	public CommunityGetResponseDto getCommunity(String communityId) {

		Community community = communityRepository.findById(communityId).orElse(null);
		
		if(community == null)
			return null;
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		community.plusCommunityHit();
		
		CommunityGetResponseDto communityGetResponseDto = CommunityGetResponseDto.builder()
				.communityId(community.getId())
				.title(community.getTitle())
				.content(community.getContent())
				.hit(community.getHit())
				.regTime(community.getRegTime().format(dateTimeFormatter))
				.userId(community.getUser().getId())
				.userNickname(community.getUser().getNickname())
				.build();
		
		communityRepository.save(community);
		
		return communityGetResponseDto;
		
	}

	@Override
	public CommunityGetListResponseDto getCommunityList(int pageNumber) {
		List<CommunityGetResponseDto> communityGetResponselist = new ArrayList<CommunityGetResponseDto>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		Page<Community> communiyPages = communityRepository.findByDelYn(false, PageRequest.of(pageNumber-1, 10, Sort.Direction.DESC, "regTime" ));
		communiyPages.forEach(community -> {
			CommunityGetResponseDto communityGetResponseDto = CommunityGetResponseDto.builder()
					.communityId(community.getId())
					.userId(community.getUser().getId())
					.userNickname(community.getUser().getNickname())
					.title(community.getTitle())
					.hit(community.getHit())
					.regTime(community.getRegTime().format(dateTimeFormatter))
					.build();
			
			communityGetResponselist.add(communityGetResponseDto);
		});
		
		CommunityGetListResponseDto communityGetListResponseDto = CommunityGetListResponseDto.builder()
				.communityGetResponselist(communityGetResponselist)
				.totalPage(communiyPages.getTotalPages())
				.build();
		
		return communityGetListResponseDto;
	}

	@Override
	public CommunityGetListResponseDto getMyCommunityList(String accessToken) {
		List<CommunityGetResponseDto> communityGetResponseDtos = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User user = jwtTokenService.convertTokenToUser(accessToken);

		List<Community> communityList = communityRepository.findByUser(user);

		for (Community community : communityList) {
			CommunityGetResponseDto communityGetResponseDto = CommunityGetResponseDto.builder()
					.communityId(community.getId())
					.userId(community.getUser().getId())
					.userNickname(community.getUser().getNickname())
					.title(community.getTitle())
					.content(community.getContent())
					.hit(community.getHit())
					.regTime(community.getRegTime().format(dateTimeFormatter))
					.build();

			communityGetResponseDtos.add(communityGetResponseDto);
		}
		CommunityGetListResponseDto communityGetListResponseDto = CommunityGetListResponseDto.builder()
				.communityGetResponselist(communityGetResponseDtos)
				.build();

		return communityGetListResponseDto;
	}

	@Override
	public CommunityGetListResponseDto getCommunityList() {
		List<CommunityGetResponseDto> communityGetResponselist = new ArrayList<CommunityGetResponseDto>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		List<Community> communiylist = communityRepository.findByDelYnOrderByRegTimeDesc(false);
		communiylist.forEach(community -> {
			CommunityGetResponseDto communityGetResponseDto = CommunityGetResponseDto.builder()
					.communityId(community.getId())
					.userId(community.getUser().getId())
					.userNickname(community.getUser().getNickname())
					.title(community.getTitle())
					.hit(community.getHit())
					.regTime(community.getRegTime().format(dateTimeFormatter))
					.build();
			
			communityGetResponselist.add(communityGetResponseDto);
		});
		
		CommunityGetListResponseDto communityGetListResponseDto = CommunityGetListResponseDto.builder()
				.communityGetResponselist(communityGetResponselist)
				.build();
		
		return communityGetListResponseDto;
	}

	@Transactional
	@Override
	public Community modifyCommunity(CommunityModifyRequestDto communityUpdateRequestDto) {
		Community community = communityRepository.findById(communityUpdateRequestDto.getCommunityId()).orElse(null);
		
		if(community == null)
			return null;
		
		community.modifyCommunity(
				communityUpdateRequestDto.getTitle(), 
				communityUpdateRequestDto.getContent());
		
		return communityRepository.save(community);
	}

	@Transactional
	@Override
	public Community deleteCommunity(String communityId) {
		Community community = communityRepository.findById(communityId).orElse(null);
		
		if(community == null)
			return null;
		
		community.deleteCommunity();
		
		return communityRepository.save(community);
	}

}
