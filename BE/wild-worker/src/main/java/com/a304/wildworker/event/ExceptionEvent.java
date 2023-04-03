package com.a304.wildworker.event;

import com.a304.wildworker.event.common.DomainEvent;
import com.a304.wildworker.exception.base.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionEvent implements DomainEvent {

    String sessionId;
    CustomException e;
}
