package com.mk.api.controller;


import java.util.List;

import com.google.api.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.api.dto.request.ItemRegisterRequestDto;
import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.dto.response.ItemGetListResponseDto;
import com.mk.api.dto.response.ItemGetResponseDto;
import com.mk.api.service.ItemService;
import com.mk.db.entity.Item;
import com.mk.elastic.document.Itemsearch;
import com.mk.elastic.search.SearchRequestDTO;
import com.mk.elastic.service.ItemSearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "상품 API", tags = { "Item" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class ItemController {

	private final ItemService itemService;
	
	private final ItemSearchService itemSearchService;
	
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
		Item item = itemService.registerItem(accessToken, multipartFile, itemRegisterRequestDto);
		if(item != null) {
			itemSearchService.registerItemSearch(item.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}

	@GetMapping("/list")
	@ApiOperation(value = "상품 목록 불러오기", notes="<strong>상품 목록을 불러온다.</strong>")
	@ApiResponses({
			@ApiResponse(code=200, message="상품을 정상적으로 조회하였습니다."),
			@ApiResponse(code=204, message="상품 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getItemList(){
		List<ItemGetResponseDto> itemGetResponseDto = itemService.getItemList();
		if(itemGetResponseDto != null) {
			log.info("files : " +itemGetResponseDto.get(0).getFiles());
			return ResponseEntity.status(HttpStatus.OK).body(ItemGetListResponseDto.builder().list(itemGetResponseDto).build());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}

	@GetMapping("/list/myItem")
	@ApiOperation(value = "상품 목록 불러오기", notes="<strong>상품 목록을 불러온다.</strong>")
	@ApiResponses({
			@ApiResponse(code=200, message="상품을 정상적으로 조회하였습니다."),
			@ApiResponse(code=204, message="상품 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getMyItemList(@ApiIgnore @RequestHeader("Authorization") String accessToken){
		List<ItemGetResponseDto> itemGetResponseDto = itemService.getMyItemList(accessToken);
		if(itemGetResponseDto != null) {
			log.info("files : " +itemGetResponseDto.get(0).getFiles());
			return ResponseEntity.status(HttpStatus.OK).body(ItemGetListResponseDto.builder().list(itemGetResponseDto).build());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}

	@GetMapping("/list/{pageNumber}")
	@ApiOperation(value = "상품 목록 불러오기", notes="<strong>상품 목록을 불러온다.</strong>")
	@ApiResponses({
			@ApiResponse(code=200, message="상품을 정상적으로 조회하였습니다."),
			@ApiResponse(code=204, message="상품 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> getItemListByPage(@PathVariable("pageNumber") int pageNumber){
		ItemGetListResponseDto dto = new ItemGetListResponseDto();
		List<ItemGetResponseDto> itemGetResponseDto = itemService.getItemList(pageNumber,dto);

		if(itemGetResponseDto != null) {
//			log.info("files : " +itemGetResponseDto.get(0).getFiles());
			dto.setList(itemGetResponseDto);
			dto.setMessage("Success");
			dto.setStatusCode(200);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
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
	
	@PostMapping("/pages")
	@ApiOperation(value = "전체 페이지 수 불러오기", notes="<strong>전체 페이지 수를 불러옵니다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="정상적으로 조회하였습니다."),
		@ApiResponse(code=204, message="조회를 실패했습니다.")
	})
	public int getPages(@RequestBody final SearchRequestDTO dto){
			return itemSearchService.getSearchPages(dto);
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
		if(itemService.modifyItem(itemModifyRequestDto, multipartFile) != null) {
			itemSearchService.modifyItemsearch(itemModifyRequestDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
		}
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
		if(itemService.deleteItem(itemId) != null) {
			itemSearchService.deleteItemsearch(itemId);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}

    @PostMapping("/search")
    @ApiOperation(value = "상품  검색하기", notes="<strong>입력한 상품을 검색한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="상품이 정상적으로 검색되었습니다."),
		@ApiResponse(code=204, message="상품 검색을 실패했습니다.")
	})
    public List<Itemsearch> search(@RequestBody final SearchRequestDTO dto) {
		return itemSearchService.search(dto);
    }


	@PostMapping("/purchase/{itemId}")
	@ApiOperation(value="상품 구매", notes="<strong>해당 상품을 구매 처리 한다.</strong>")
	@ApiResponses({
		@ApiResponse(code=200, message="상품 구매가 정상적으로 이루어졌습니다.."),
		@ApiResponse(code=204, message="구매 처리가 실패되었습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> purchaseItem (
			@PathVariable("itemId") @RequestBody @ApiParam(value = "조회할 상품 ID", required = true) String itemId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken){


		if (itemService.purchaseItem(accessToken, itemId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BaseResponseDto.of(HttpStatus.OK.value(), "Success"));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}

	@GetMapping("/purchase/list")
	@ApiOperation(value="구매 이력 조회", notes="<strong>구매 이력을 조회 한다.</strong>")
	@ApiResponses({
			@ApiResponse(code=200, message="조회가 정상적으로 이루어졌습니다.."),
			@ApiResponse(code=204, message="구매이력 조회를 실패했습니다.")
	})
	public ResponseEntity<? extends  BaseResponseDto> purchaseList (@ApiIgnore @RequestHeader("Authorization") String accessToken) {
		List<ItemGetResponseDto> itemGetResponseDto = itemService.purchaseList(accessToken);

		if (itemGetResponseDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(ItemGetListResponseDto.builder().list(itemGetResponseDto).build());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
	}

	@PostMapping("/purchase/confirm/{itemId}")
	public ResponseEntity<? extends BaseResponseDto> purchaseConfirm(
			@PathVariable("itemId") @RequestBody @ApiParam(value = "조회할 상품 ID", required = true) String itemId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken) {

		if (itemService.purchaseConfirm(accessToken, itemId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BaseResponseDto.of(HttpStatus.OK.value(), "Success"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}

	@DeleteMapping("/cancel/{itemId}")
	@ApiOperation(value="구매 취소", notes="<strong>구매를 취소한다.</strong>")
	@ApiResponses({
			@ApiResponse(code=200, message="구매 취소가 이루어졌습니다.."),
			@ApiResponse(code=204, message="구매 취소 요청이 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponseDto> cancelPurchase (
			@PathVariable("itemId") @RequestBody @ApiParam(value = "조회할 상품 ID", required = true) String itemId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken) {

		if (itemService.cancelPurchase(accessToken, itemId)) {
			return ResponseEntity.status(HttpStatus.OK).body(BaseResponseDto.of(HttpStatus.OK.value(), "Success"));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
	}
}
