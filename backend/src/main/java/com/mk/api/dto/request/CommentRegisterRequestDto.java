package com.mk.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("CommentRegisterRequestDto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentRegisterRequestDto {
	
	@ApiModelProperty(name="커뮤니티 ID(pk)", example="00000000-0000-0000-0000-00000000")
	private String communityId;
	
	@ApiModelProperty(name="내용", example = "내용이에요~")
	private String content;
	
}
