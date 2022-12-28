package com.cloneweek.hanghaebnb.dto.ResponseDto;

import com.cloneweek.hanghaebnb.util.exception.StatusMsgCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseLikeDto extends ResponseMsgDto {

    private Long id;
    private boolean likeCheck;

    public ResponseLikeDto(StatusMsgCode statusMsgCode, Long id, boolean likeCheck) {
        super(statusMsgCode);
        this.id = id;
        this.likeCheck = likeCheck;
    }
}
