package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.ConflictException;

public class AlreadySendException extends ConflictException {

    public AlreadySendException() {
        super("이미 메세지를 보낸 사용자입니다.");
    }
}
