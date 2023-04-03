package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.dto.response.MatchCancelResponse;
import com.a304.wildworker.dto.response.common.MiniGameType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.event.MatchCancelEvent;
import com.a304.wildworker.service.MessageService;
import com.a304.wildworker.service.MiniGameLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchCancelEventHandler {

    private final MessageService messageService;
    private final MiniGameLogService miniGameLogService;

    @EventListener
    public void sendMessage(MatchCancelEvent event) {
        log.info("event occur: MatchCancelEvent - send message: {}", event);
        Match match = event.getMatch();
        match.getUsers().forEach(activeUser -> {
            String sessionId = activeUser.getWebsocketSessionId();
            WSBaseResponse<MatchCancelResponse> response = WSBaseResponse
                    .miniGame(MiniGameType.CANCEL)
                    .data(MatchCancelResponse.of(match.isRunner(activeUser.getUserId())));
            messageService.sendToUser(sessionId, response);
        });
    }

    @EventListener
    public void saveLog(MatchCancelEvent event) {
        log.info("event occur: MatchCancelEvent - saveLog: {}", event);
        miniGameLogService.saveLog(event.getMatch());
    }
}
