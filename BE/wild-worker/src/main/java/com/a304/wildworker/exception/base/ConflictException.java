package com.a304.wildworker.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ConflictException extends RestCustomException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
