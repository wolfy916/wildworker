package com.a304.wildworker.exception.base;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
