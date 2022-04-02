package com.mk.api.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("ItemGetResponseDto")
public class HotItemGetResponseDto extends BaseResponseDto {

	@ApiModelProperty(name="아이템 ID")
	private String itemId;
	
	@ApiModelProperty(name="구분", example="대여")
	private String division;

	@ApiModelProperty(name="상품명", example="유모차")
	private String itemName;

	@ApiModelProperty(name="아이템 상태", example="판매 중")
	private String status;

	@ApiModelProperty(name="카테고리", example="B01")
	private String category;

	@ApiModelProperty(name="가격", example="30000")
	private int price;
	
	@ApiModelProperty(name="설명", example = "설명이에요~")
	private String description;
	
	@ApiModelProperty(name="관심등록 수", example = "10")
	private int count;
	
	@ApiModelProperty(name="등록일시", example = "2022년 01월 01일 ")
	private String regDate;
	
	@ApiModelProperty(name="법정동이름", example = "삼평동")
	private String bname;
	
	@ApiModelProperty(name="파일", example="orginFile, 파일 URL")
	private List<String> files;
	
}
