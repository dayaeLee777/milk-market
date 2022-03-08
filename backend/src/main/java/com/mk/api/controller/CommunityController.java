package com.mk.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.api.dto.request.CommunityRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.service.CommunityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "커뮤니티 API", tags = { "Community" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {
	
	private final CommunityService communityService;
	
	@PostMapping
	@ApiOperation(value = "커뮤니티 등록하기", notes="<strong>회원이 작성한 커뮤니티를 등록한다.</strong><br/>")
	@ApiResponses({
		@ApiResponse(code=201, message="커뮤니티가 정상적으로 등록되었습니다."),
		@ApiResponse(code=401, message="인증되지 않은 사용자입니다."),
		@ApiResponse(code=409, message="커뮤니티 등록을 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> regist(
		@RequestBody @ApiParam(value = "등록할 커뮤니티", required = true) CommunityRegisterRequestDto communityRegisterRequestDto){
		if(communityService.registerCommunity(communityRegisterRequestDto) != null)
			return ResponseEntity.status(200).body(BaseResponseDto.of(200, "Success"));
		return ResponseEntity.status(409).body(BaseResponseDto.of(409, "Fail"));
	}
}
