package com.a304.wildworker.exception;

import com.a304.wildworker.exception.custom_exception.BadRequestException;

public class TestException extends BadRequestException {

    public TestException(String message) {
        super(message);
    }
}
