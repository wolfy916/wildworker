package com.a304.wildworker.exception;

import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.common.TitleCode;
import com.a304.wildworker.exception.base.NotFoundException;

public class NoSuchCodeException extends NotFoundException {

    public NoSuchCodeException(MiniGameCode code) {
        super("존재하지 않는 미니게임 입니다. " + code);
    }

    public NoSuchCodeException(TitleCode code) {
        super("존재하지 않는 호칭 입니다. " + code);
    }
}
