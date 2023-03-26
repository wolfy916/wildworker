package com.a304.wildworker.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ForbiddenException extends CustomException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
