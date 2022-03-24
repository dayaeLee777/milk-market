package com.mk.api.controller;


import java.util.List;

import com.mk.api.service.UserService;
import com.mk.db.code.Code;
import com.mk.db.entity.Item;
import com.mk.db.repository.ItemRepository;
import com.mk.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.api.service.ItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;

@Slf4j
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
		@ApiParam(value = "등록할 상품", required = true) @RequestPart ItemRegisterRequestDto itemRegisterRequestDto){
		System.out.println(itemRegisterRequestDto.toString());
		if(itemService.registerItem(accessToken, multipartFile, itemRegisterRequestDto) != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}
	
	@GetMapping("/{itemId}")
	@ApiOperation(value = "상품 불러오기", notes="<strong>itemId에 해당하는 커뮤니티를 불러온다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="상품을 정상적으로 조회하였습니다."),
		@ApiResponse(code=204, message="상품 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getItem(
			@PathVariable("itemId") @RequestBody @ApiParam(value = "조회할 상품 ID", required = true) String itemId){
		ItemGetResponseDto itemGetResponseDto = itemService.getItem(itemId);
		if(itemGetResponseDto != null)
			return ResponseEntity.status(HttpStatus.OK).body(itemGetResponseDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
	@PutMapping
	@ApiOperation(value = "상품 수정하기", notes="<strong>작성한 상품을 수정한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=201, message="상품이 정상적으로 수정되었습니다."),
		@ApiResponse(code=204, message="상품 수정을 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> modify(
			@ApiParam(value = "수정할 상품정보") @RequestPart(required = true) ItemModifyRequestDto itemModifyRequestDto,
			@ApiParam(value="다중 파일 업로드") @RequestPart(required = false) List<MultipartFile> multipartFile){
		if(itemService.modifyItem(itemModifyRequestDto, multipartFile) != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}
	
	@PutMapping("/delete/{itemId}")
	@ApiOperation(value = "상품 삭제하기", notes="<strong>작성한 상품을 삭제한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=202, message="상품이 정상적으로 삭제되었습니다."),
		@ApiResponse(code=204, message="상품 삭제를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> delete(
			@PathVariable("itemId") @RequestBody @ApiParam(value = "삭제할 상품 ID ", required = true) String itemId){
		if(itemService.deleteItem(itemId) != null)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}


}
