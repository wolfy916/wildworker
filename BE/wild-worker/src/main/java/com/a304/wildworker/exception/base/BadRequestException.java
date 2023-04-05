package com.a304.wildworker.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BadRequestException extends RestCustomException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
