package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.UnauthorizedException;

public class NotLoginException extends UnauthorizedException {

    public NotLoginException() {
        super("로그인하지 않은 사용자입니다.");
    }
}
