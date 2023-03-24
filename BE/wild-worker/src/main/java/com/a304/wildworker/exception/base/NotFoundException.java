package com.a304.wildworker.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
