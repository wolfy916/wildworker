package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class NotCurrentStationException extends BadRequestException {

    public NotCurrentStationException(long currentStationId, long subscribeStationId) {
        super("현재 역이 아닙니다. (현재역: " + currentStationId + ", 구독역: " + subscribeStationId + ")");
    }
}
