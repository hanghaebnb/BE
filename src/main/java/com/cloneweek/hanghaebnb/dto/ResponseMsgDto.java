package com.cloneweek.hanghaebnb.dto;

import com.cloneweek.hanghaebnb.common.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

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
