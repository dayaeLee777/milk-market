package com.mk.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("CommentModifyRequestDto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentModifyRequestDto {
	
	@ApiModelProperty(name="댓글 ID(pk)", example="00000000-0000-0000-0000-00000000")
	private String commentId;
	
	@ApiModelProperty(name="내용", example = "내용이에요~")
	private String content;
	
}
