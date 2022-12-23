package com.cloneweek.hanghaebnb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

//    @Pattern(regexp = "[a-z0-9]{4,10}")
    private String email;

//    @Pattern(regexp = "[a-z0-9A-Z!@#$%^&*]{8,15}")
    private String password;

    private String nickname;
}
