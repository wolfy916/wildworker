package com.a304.wildworker.interceptor;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.sessionuser.PrincipalDetails;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import java.security.Principal;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageInterceptor implements ChannelInterceptor {

    private final ActiveUserRepository activeUserRepository;


    private ActiveUser findOrSaveActiveUser(SessionUser sessionUser) {
        return activeUserRepository.findById(sessionUser.getId())
                .orElseGet(() -> activeUserRepository.save(new ActiveUser(sessionUser.getId())));
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
        log.debug("ws MessageInterceptor(preSend): {}", accessor.getMessageType());

        Principal beforeUser = accessor.getUser();
        if (!(beforeUser instanceof OAuth2AuthenticationToken)) {
            throw new RuntimeException("Principal 오류");     //TODO: change NotLoginException
        }

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) beforeUser;
        SessionUser sessionUser = ((PrincipalDetails) token.getPrincipal()).getSessionUser();

        ActiveUser activeUser = findOrSaveActiveUser(sessionUser);
        activeUser.setWebsocketSessionId(accessor.getSessionId());
        accessor.setUser(activeUser);
        accessor.setLeaveMutable(true);
        log.debug("-- activeUser: {}", activeUser);

        switch (Objects.requireNonNull(accessor.getMessageType())) {
            case CONNECT:
                break;
            case SUBSCRIBE:
                //TODO: ADD STATION pool
                break;
            case UNSUBSCRIBE:
                //TODO: remove STATION pool
                break;
            case DISCONNECT:
                activeUserRepository.deleteById(sessionUser.getId());
                break;
        }

        return MessageBuilder.fromMessage(message).setHeaders(accessor).build();
    }
}
