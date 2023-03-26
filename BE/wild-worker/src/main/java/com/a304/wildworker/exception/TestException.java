package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class TestException extends BadRequestException {

    public TestException(String message) {
        super(message);
    }
}
