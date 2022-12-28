package com.cloneweek.hanghaebnb.dto.ResponseDto;

import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBoolDto extends ResponseMsgDto {
    private boolean result;

    public ResponseBoolDto(StatusMsgCode statusMsgCode, boolean result) {
        super(statusMsgCode);
        this.result = result;
    }
}
