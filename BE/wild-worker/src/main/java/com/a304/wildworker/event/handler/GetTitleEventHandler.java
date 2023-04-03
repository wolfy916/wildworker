package com.a304.wildworker.event.handler;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.title.TitleAwarded;
import com.a304.wildworker.domain.title.TitleAwardedRepository;
import com.a304.wildworker.dto.response.TitleGetResponse;
import com.a304.wildworker.dto.response.common.TitleType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.event.GetTitleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class GetTitleEventHandler {

    private final ActiveUserRepository activeUserRepository;
    private final TitleAwardedRepository titleAwardedRepository;

    private final SimpMessagingTemplate messagingTemplate;

    /* 얻은 칭호 추가 */
    @Transactional
    @Order(1)
    @EventListener
    public void insertTitleAwarded(GetTitleEvent event) {
        TitleAwarded titleAwarded = new TitleAwarded(event.getUser(), event.getTitle());
        titleAwardedRepository.save(titleAwarded);
    }

    /* 칭호 획득 Noti 송신 */
    @Order(2)
    @EventListener
    public void sendTitleNotification(GetTitleEvent event) {
        activeUserRepository.findById(event.getUser().getId()).ifPresent(activeUser -> {
            WSBaseResponse<TitleGetResponse> response = WSBaseResponse.title(TitleType.GET)
                    .data(new TitleGetResponse(event.getTitle().getId(),
                            event.getTitle().getName()));

            messagingTemplate.convertAndSendToUser(activeUser.getWebsocketSessionId(),
                    "/queue",
                    response,
                    WebSocketUtils.createHeaders(activeUser.getWebsocketSessionId()));
        });
    }
}
