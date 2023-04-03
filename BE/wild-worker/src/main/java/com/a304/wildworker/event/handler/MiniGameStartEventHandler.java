package com.a304.wildworker.event.handler;

import com.a304.wildworker.event.MiniGameStartEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MiniGameStartEventHandler {

    @EventListener
    public void setReceiveTimeLimit(MiniGameStartEvent event) {

    }
}
