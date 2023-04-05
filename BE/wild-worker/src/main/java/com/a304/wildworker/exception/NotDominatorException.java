package com.a304.wildworker.exception;

import com.a304.wildworker.dto.response.common.WSExceptionType;
import com.a304.wildworker.exception.base.WSCustomException;

public class NotDominatorException extends WSCustomException {

    public NotDominatorException() {
        super(WSExceptionType.NOT_DOMINATOR, "지배 중인 역이 없습니다.");
    }
}
