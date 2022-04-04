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
@ApiModel("CommentGetResponseDto")
public class CommentGetResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="댓글 ID", example="00000000-0000-0000-0000-00000000")
	private String commentId;
	
	@ApiModelProperty(name="작성자 ID", example="00000000-0000-0000-0000-00000000")
	private String userId;
	
	@ApiModelProperty(name="작성자 닉네임", example = "닉네임")
	private String userNickname;
	
	@ApiModelProperty(name="댓글 내용", example = "내용입니다")
	private String content;
	
	@ApiModelProperty(name="등록일시", example="2022-01-27 10:08:12")
	private String regTime;
	
	@ApiModelProperty(name="프로필 이미지", example="http://~~")
	private String profileImage;
	
}
