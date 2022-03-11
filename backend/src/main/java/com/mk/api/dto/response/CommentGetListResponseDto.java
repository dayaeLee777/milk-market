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
@ApiModel("CommentGetListResponseDto")
public class CommentGetListResponseDto extends BaseResponseDto {
	
	@ApiModelProperty(name="커뮤니티 ID")
	private List<CommentGetResponseDto> commentGetListResponseDto;
	
}
