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
@ApiModel("ItemGetListResponseDto")
public class ItemGetListResponseDto extends BaseResponseDto{

    @ApiModelProperty(name="아이템 목록")
    List<ItemGetResponseDto> list;
}
