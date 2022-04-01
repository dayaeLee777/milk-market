package com.mk.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SignUpReq {
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 16자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        private String nickname;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    private String profileImage;
    
    @ApiModelProperty(name="법정동/법정리 코드", example="4113510900")
    private String bcode;

	@ApiModelProperty(name="법정동/법정리 이름", example="삼평동")
    private String bname;

    @ApiModelProperty(name="시군구 이름", example="과천시 문원동")
    private String sigungu;
}
