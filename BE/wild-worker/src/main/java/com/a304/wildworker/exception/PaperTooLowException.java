package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class PaperTooLowException extends BadRequestException {

    public PaperTooLowException() {
        super("서류가 너무 적습니다.");
    }
}
