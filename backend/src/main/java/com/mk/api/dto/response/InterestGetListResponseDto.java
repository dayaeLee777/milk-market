package com.mk.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("InterestGetListResponseDto")
public class InterestGetListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="관심 상품 목록")
	private List<ItemGetResponseDto> InterestListResponseDto;
	
}
