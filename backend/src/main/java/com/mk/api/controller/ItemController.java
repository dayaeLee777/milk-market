package com.mk.api.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "상품 API", tags = { "Item" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

	private final ItemService itemService;
	
	@PostMapping(consumes = {"multipart/form-data"})
	@ApiOperation(value = "상품 등록하기", notes="<strong>회원이 작성한 상품를 등록한다.</strong><br/>")
	@ApiResponses({
		@ApiResponse(code=201, message="상품을 정상적으로 등록되었습니다."),
		@ApiResponse(code=400, message="상품 등록을 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> regist(
		@ApiIgnore @RequestHeader("Authorization") String accessToken,
		@ApiParam(value="다중 파일 업로드") @RequestPart(required = false) List<MultipartFile> multipartFile,
		@RequestBody @ApiParam(value = "등록할 상품", required = true) ItemRegisterRequestDto itemRegisterRequestDto){
		if(itemService.registerItem(accessToken, multipartFile, itemRegisterRequestDto) != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}

}
