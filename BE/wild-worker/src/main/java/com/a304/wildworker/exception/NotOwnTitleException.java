package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class NotOwnTitleException extends BadRequestException {

    public NotOwnTitleException() {
        super("보유하지 않은 칭호입니다 !");
    }
}
