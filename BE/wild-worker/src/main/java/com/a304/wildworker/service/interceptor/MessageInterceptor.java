package com.a304.wildworker.service.interceptor;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.sessionuser.PrincipalDetails;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.exception.NotCurrentStationException;
import com.a304.wildworker.exception.NotLoginException;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.base.CustomException;
import com.a304.wildworker.service.EventService;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageInterceptor implements ChannelInterceptor {

    private final ActiveUserRepository activeUserRepository;
    private final StationRepository stationRepository;
    private final EventService eventService;

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
        log.debug("ws MessageInterceptor(preSend): {}", accessor.getMessageType());
        try {
            Principal beforeUser = accessor.getUser();
            if (!(beforeUser instanceof OAuth2AuthenticationToken)) {
                throw new NotLoginException();
            }

            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) beforeUser;
            SessionUser sessionUser = ((PrincipalDetails) token.getPrincipal()).getSessionUser();

            ActiveUser activeUser = findOrSaveActiveUser(sessionUser);
            activeUser.setWebsocketSessionId(accessor.getSessionId());
            accessor.setUser(activeUser);
            accessor.setLeaveMutable(true);
            log.debug("-- activeUser: {}", activeUser);

            SimpMessageType messageType = Objects.requireNonNull(accessor.getMessageType());
            switch (messageType) {
                case SUBSCRIBE:
                case UNSUBSCRIBE:
                    String destination = accessor.getDestination();
                    subUnsubStation(messageType, destination, activeUser);
                    break;
                case DISCONNECT:
                    activeUserRepository.deleteById(sessionUser.getId());
                    break;
            }
        } catch (CustomException e) {
            eventService.handleException(accessor.getSessionId(), e);
        }
        return MessageBuilder.fromMessage(message).setHeaders(accessor).build();
    }

    private ActiveUser findOrSaveActiveUser(SessionUser sessionUser) {
        return activeUserRepository.findById(sessionUser.getId())
                .orElseGet(() -> activeUserRepository.save(new ActiveUser(sessionUser.getId())));
    }

    private Long parseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isDestinationStation(String destination) {
        return destination != null && destination.startsWith("/sub/stations");
    }

    public long getStationIdFromDestination(String destination) {
        String stationIdStr = Objects.requireNonNull(destination)
                .substring(destination.lastIndexOf("/") + 1);
        return Optional.ofNullable(parseLong(stationIdStr))
                .filter(stationRepository::existsById)
                .orElseThrow(StationNotFoundException::new);
    }

    public void subUnsubStation(SimpMessageType type, String destination, ActiveUser activeUser) {
        if (isDestinationStation(destination)) {
            long stationId = getStationIdFromDestination(destination);
            long curStationId = activeUser.getStationId();
            if (curStationId == stationId) {
                eventService.subUnsubStation(activeUser, type == SimpMessageType.SUBSCRIBE);
            } else {
                throw new NotCurrentStationException(curStationId, stationId);
            }
        }
    }
}
