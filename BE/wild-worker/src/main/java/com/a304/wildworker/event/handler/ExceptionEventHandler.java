package com.a304.wildworker.event.handler;

import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.event.ExceptionEvent;
import com.a304.wildworker.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExceptionEventHandler {

    private final MessageService messageService;

    @Async
    @EventListener
    public void handle(ExceptionEvent event) {
        WSBaseResponse<?> response = WSBaseResponse.exception(event.getE());
        messageService.sendToUser(event.getSessionId(), response);
    }
}
