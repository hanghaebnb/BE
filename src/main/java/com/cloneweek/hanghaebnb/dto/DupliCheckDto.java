package com.cloneweek.hanghaebnb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class DupliCheckDto {

//    @Email
//    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*..[a-zA-Z]{2,4}$",
//            message = "올바른 형식의 이메일 주소여야 합니다")
    @Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$",
            message = "올바른 형식의 이메일 주소여야 합니다")
    private String email;

    private String nickname;
}
