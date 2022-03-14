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
@ApiModel("CommunityGetListResponseDto")
public class CommunityGetListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="커뮤니티 ID")
	private List<CommunityGetResponseDto> communityGetResponselist;

	@ApiModelProperty(name="전체 페이지 수", example = "3")
	private int totalPage;
	
}
