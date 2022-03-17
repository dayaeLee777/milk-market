package com.mk.api.dto.request;

import java.time.LocalDateTime;

import com.mk.db.code.Code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("ItemRegisterRequestDto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ItemRegisterRequestDto {
	
	@ApiModelProperty(name="구분", example="대여 : A01, 판매 : A02")
	private Code division;

	@ApiModelProperty(name="상품명", example="유모차")
	private String itemName;

	@ApiModelProperty(name="카테고리", example="B01")
	private Code category;

	@ApiModelProperty(name="가격", example="30000")
	private int price;
	
	@ApiModelProperty(name="설명", example = "설명이에요~")
	private String description;

	@ApiModelProperty(name="대여시작일", example="2022-01-01 00:00:00")
	private LocalDateTime rentStartDate;

	@ApiModelProperty(name="대여종료일", example="2022-01-01 00:00:00")
	private LocalDateTime rentEndDate;
}
