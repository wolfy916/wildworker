package com.a304.wildworker.exception.base;

import com.a304.wildworker.dto.response.common.WSExceptionType;
import lombok.Getter;

@Getter
public abstract class WSCustomException extends CustomException {

    private final WSExceptionType wsExceptionType;

    public WSCustomException(WSExceptionType wsExceptionType, String message) {
        super(message);
        this.wsExceptionType = wsExceptionType;
    }
}
