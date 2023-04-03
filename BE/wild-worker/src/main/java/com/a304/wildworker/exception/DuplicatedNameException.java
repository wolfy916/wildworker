package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.ConflictException;

public class DuplicatedNameException extends ConflictException {

    public DuplicatedNameException() {
        super("동일한 닉네임이 존재합니다.");
    }
}
