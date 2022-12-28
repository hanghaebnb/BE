package com.cloneweek.hanghaebnb.dto.RequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class DupliCheckDto {

    @Pattern(regexp="^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message = "올바른 형식의 이메일 주소여야 합니다")
    private String email;

    private String nickname;
}
