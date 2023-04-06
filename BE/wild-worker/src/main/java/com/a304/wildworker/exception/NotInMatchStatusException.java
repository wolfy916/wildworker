package com.a304.wildworker.exception;

import com.a304.wildworker.domain.common.MatchStatus;
import com.a304.wildworker.exception.base.BadRequestException;

public class NotInMatchStatusException extends BadRequestException {

    public NotInMatchStatusException(MatchStatus current, MatchStatus request) {
        super("미니게임 진행 상태와 다른 요청입니다.(현재 게임 진행 상태: " + current.name() +
                ", 요청: " + request.name() + ")");
    }
}
