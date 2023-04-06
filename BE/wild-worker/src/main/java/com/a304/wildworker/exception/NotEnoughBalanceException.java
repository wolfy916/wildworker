package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class NotEnoughBalanceException extends BadRequestException {

    public NotEnoughBalanceException(Long commission, Long value) {
        super("잔액이 부족합니다. (현재 잔액: " + commission + ", 변동: " + value + ")");
    }
}
