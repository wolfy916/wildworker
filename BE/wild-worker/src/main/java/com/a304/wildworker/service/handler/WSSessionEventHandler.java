package com.a304.wildworker.service.handler;

import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class WSSessionEventHandler {

    private final ActiveUserRepository activeUserRepository;

    @EventListener
    public void onConnect(SessionConnectEvent event) {
        log.info("ws event occur(connect): {}", event);
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        var user = accessor.getUser();
        log.info("-- user: {}", user);
    }

    @EventListener
    public void onSubscribe(SessionSubscribeEvent event) {
        log.info("ws event occur(subscribe): {}", event);

    }

    @EventListener
    public void onUnsubscribe(SessionUnsubscribeEvent event) {
        log.info("ws event occur(unsubscribe): {}", event);

    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        log.info("ws event occur(disconnect): {}", event);

    }

}
