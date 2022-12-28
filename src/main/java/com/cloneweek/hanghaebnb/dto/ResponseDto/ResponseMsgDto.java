package com.cloneweek.hanghaebnb.dto.ResponseDto;

import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMsgDto {

    private int statusCode;
    private String message;

    public ResponseMsgDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseMsgDto(StatusMsgCode statusMsgCode){
        this.statusCode = statusMsgCode.getHttpStatus().value();
        this.message = statusMsgCode.getDetail();
    }
}
