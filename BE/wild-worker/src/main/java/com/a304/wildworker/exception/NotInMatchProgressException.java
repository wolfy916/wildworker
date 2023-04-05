package com.a304.wildworker.exception;

import com.a304.wildworker.domain.common.MatchStatus;
import com.a304.wildworker.exception.base.BadRequestException;

public class NotInMatchProgressException extends BadRequestException {

    public NotInMatchProgressException(MatchStatus current, MatchStatus request) {
        super("미니게임 진행 상태와 다른 요청입니다.(현재: " + current.name() + ", 요청: " + request.name() + ")");
    }
}
