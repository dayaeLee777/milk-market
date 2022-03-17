package com.mk.api.dto.response;

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
@ApiModel("CommunityGetResponseDto")
public class CommunityGetResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="커뮤니티 ID")
	private String communityId;
	
	@ApiModelProperty(name="작성자 ID")
	private String userId;
	
	@ApiModelProperty(name="작성자 닉네임")
	private String userNickname;
	
	@ApiModelProperty(name="커뮤니티 제목", example = "제목입니다")
	private String title;
	
	@ApiModelProperty(name="커뮤니티 내용", example = "내용입니다")
	private String content;

	@ApiModelProperty(name="조회수", example="21")
	private int hit;
	
	@ApiModelProperty(name="등록일시", example="2022-01-27 10:08:12")
	private String regTime;
	
}
