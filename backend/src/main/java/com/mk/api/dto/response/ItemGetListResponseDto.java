package com.mk.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("ItemGetListResponseDto")
public class ItemGetListResponseDto extends BaseResponseDto{

    @ApiModelProperty(name="아이템 목록")
    List<ItemGetResponseDto> list;

    @ApiModelProperty(name="총 페이지 수")
    int totalPages;
}
