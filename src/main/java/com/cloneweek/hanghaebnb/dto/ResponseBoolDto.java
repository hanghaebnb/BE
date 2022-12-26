package com.cloneweek.hanghaebnb.dto;

import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseBoolDto extends ResponseMsgDto{
    private boolean result;

    public ResponseBoolDto(StatusMsgCode statusMsgCode, boolean result) {
        super(statusMsgCode);
        this.result = result;
    }
}
