package com.a304.wildworker.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class RestCustomException extends CustomException {

    private final HttpStatus status;

    public RestCustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
