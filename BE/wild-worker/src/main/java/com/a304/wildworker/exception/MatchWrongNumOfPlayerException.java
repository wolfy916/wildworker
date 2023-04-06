package com.a304.wildworker.exception;

import com.a304.wildworker.exception.base.BadRequestException;

public class MatchWrongNumOfPlayerException extends BadRequestException {

    public MatchWrongNumOfPlayerException(int expected, int current) {
        super("게임에 플레이어 수가 잘못됐습니다 (expected: " + expected + ", current: " + current);
    }
}
