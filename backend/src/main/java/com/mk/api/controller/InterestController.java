package com.mk.api.controller;


import com.mk.api.dto.response.BaseResponseDto;
import com.mk.api.dto.response.HotItemGetResponseDto;
import com.mk.api.dto.response.InterestGetListResponseDto;
import com.mk.api.service.InterestService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "관심상품 API", tags = { "Interest" })
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/interest")
public class InterestController {

    private final InterestService interestService;

    //itemId = itemId : 관심상품 아이디
    @PostMapping("/{itemId}")
    public ResponseEntity<? extends BaseResponseDto> regist(
            @ApiIgnore @RequestHeader("Authorization") String accessToken,
            @PathVariable("itemId") String itemId ){
        if(interestService.registerInterest(accessToken, itemId) != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.of(HttpStatus.CREATED.value(), "Success"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseDto.of(HttpStatus.BAD_REQUEST.value(), "Fail"));
    }

    @GetMapping("/list")
    @ApiOperation(value = "관심상품 불러오기", notes="<strong>관심상품들을 불러온다.</strong>")
    @ApiResponses({
            @ApiResponse(code=200, message="관심 상품을 정상적으로 조회하였습니다."),
            @ApiResponse(code=204, message="관심 상품 조회를 실패했습니다.")
    })
    public ResponseEntity<? extends BaseResponseDto> getItem(
            @ApiIgnore @RequestHeader("Authorization") String accessToken){
        InterestGetListResponseDto interestListDto= interestService.getItemList(accessToken);
        if(interestListDto !=null)
            return ResponseEntity.status(HttpStatus.OK).body(interestListDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
    }

    @GetMapping("/lists")
    @ApiOperation(value = "관심상품 불러오기", notes="<strong>관심상품들을 불러온다.</strong>")
    @ApiResponses({
            @ApiResponse(code=200, message="관심 상품을 정상적으로 조회하였습니다."),
            @ApiResponse(code=204, message="관심 상품 조회를 실패했습니다.")
    })
    public ResponseEntity<? extends BaseResponseDto> getItem(
            @ApiIgnore @RequestHeader("Authorization") String accessToken, Pageable pageable){
        InterestGetListResponseDto interestListDto= interestService.getItemLists(accessToken,pageable);
        if(interestListDto !=null)
            return ResponseEntity.status(HttpStatus.OK).body(interestListDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
    }

    @PutMapping("/delete/{itemId}")
    @ApiOperation(value = "관심상품 삭제하기", notes="<strong>관심상품을 삭제한다.</strong>")
    @ApiResponses({
            @ApiResponse(code=202, message="관심상품을 정상적으로 삭제되었습니다."),
            @ApiResponse(code=204, message="관심상품 삭제를 실패했습니다.")
    })
    public ResponseEntity<? extends BaseResponseDto> delete(
            @PathVariable("itemId") @RequestBody @ApiParam(value = "삭제할 관심상품ID ", required = true) String itemId){
        if(interestService.deleteInterest(itemId) != null)
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(BaseResponseDto.of(HttpStatus.ACCEPTED.value(), "Success"));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
    }

    @GetMapping("/hotitem")
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "인기상품 불러오기", notes="<strong>인기상품들을 불러온다.</strong>")
    @ApiResponses({
            @ApiResponse(code=200, message="인기상품을 정상적으로 조회하였습니다."),
            @ApiResponse(code=204, message="인기상품 조회를 실패했습니다.")
    })
    public List<HotItemGetResponseDto> getHotItem(){
    	List<HotItemGetResponseDto> hotItemGetResponseDtoList = interestService.getHotItem();
//        if(hotItemGetResponseDtoList !=null)
//            return (ResponseEntity<? extends BaseResponseDto>) ResponseEntity.status(HttpStatus.OK).body(hotItemGetResponseDtoList);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BaseResponseDto.of(HttpStatus.NO_CONTENT.value(), "Fail"));
    	return hotItemGetResponseDtoList;
	}


}
