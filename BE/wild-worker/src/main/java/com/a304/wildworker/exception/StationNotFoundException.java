package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.NotFoundException;

public class StationNotFoundException extends NotFoundException {

    public StationNotFoundException() {
        super("존재하지 않는 역입니다.");
    }
}
