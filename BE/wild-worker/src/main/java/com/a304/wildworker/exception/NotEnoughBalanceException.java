package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class NotEnoughBalanceException extends BadRequestException {

    public NotEnoughBalanceException() {
        super("잔액이 부족합니다.");
    }
}
