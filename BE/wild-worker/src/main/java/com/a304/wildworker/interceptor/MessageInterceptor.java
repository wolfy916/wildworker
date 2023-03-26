package com.a304.wildworker.interceptor;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageInterceptor implements ChannelInterceptor {

    private final ActiveUserRepository activeUserRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);

        // TODO: 로그인 기능 완료 시 삭제 예정
        activeUserRepository.saveActiveUser(accessor.getSessionId(), new ActiveUser(1));

        ActiveUser activeUser = activeUserRepository.getActiveUser(accessor.getSessionId());
        accessor.setUser(activeUser);
        accessor.setLeaveMutable(true);

        return MessageBuilder.fromMessage(message).setHeaders(accessor).build();
    }
}
