package com.cloneweek.hanghaebnb.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final StatusMsgCode statusMsgCode;
}