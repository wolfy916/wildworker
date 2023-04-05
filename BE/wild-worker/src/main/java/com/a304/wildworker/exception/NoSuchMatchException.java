package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.NotFoundException;

public class NoSuchMatchException extends NotFoundException {

    public NoSuchMatchException(String id) {
        super("존재하지 않은 Match id 입니다. : " + id);
    }
}
