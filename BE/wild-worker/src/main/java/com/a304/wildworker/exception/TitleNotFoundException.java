package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.NotFoundException;

public class TitleNotFoundException extends NotFoundException {

    public TitleNotFoundException() {
        super("존재하지 않는 사용자입니다.");
    }
}
