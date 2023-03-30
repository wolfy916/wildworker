package com.a304.wildworker.interceptor;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.sessionuser.PrincipalDetails;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.exception.NotLoginException;
import com.a304.wildworker.exception.StationNotFoundException;
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
    private final ActiveStationRepository activeStationRepository;
    private final StationRepository stationRepository;

    @Override
    public Message<?> preSend(@NotNull Message<?> message, @NotNull MessageChannel channel) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
        log.debug("ws MessageInterceptor(preSend): {}", accessor.getMessageType());

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

        return MessageBuilder.fromMessage(message).setHeaders(accessor).build();
    }


    private ActiveUser findOrSaveActiveUser(SessionUser sessionUser) {
        return activeUserRepository.findById(sessionUser.getId())
                .orElseGet(() -> activeUserRepository.save(new ActiveUser(sessionUser.getId())));
    }

    private boolean isDestinationStation(String destination) {
        return destination != null && destination.startsWith("/sub/stations");
    }

    private Long parseLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
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
            Long stationId = getStationIdFromDestination(destination);
            ActiveStation activeStation = activeStationRepository.findById(stationId);
            if (type == SimpMessageType.SUBSCRIBE) {
                activeStation.subscribe(activeUser);
            } else {
                activeStation.unsubscribe(activeUser.getUserId());
            }
        }
    }

}
