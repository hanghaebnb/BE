package com.cloneweek.hanghaebnb.dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class DupliCheckDto {

    @Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message = "올바른 형식의 이메일 주소여야 합니다")
    private String email;

//    @NotBlank(message = "닉네임은 필수 입력 값입니다")
//    @Size(min = 2, max = 10, message = "최소 2자 이상, 10자 이하를 입력해주세요.")
    private String nickname;
}
