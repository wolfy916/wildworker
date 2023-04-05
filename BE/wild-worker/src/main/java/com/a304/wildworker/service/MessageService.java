package com.a304.wildworker.service;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.config.WebSocketConfig;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;


    public void sendToUser(String sessionId, WSBaseResponse<?> response) {
        messagingTemplate.convertAndSendToUser(
                sessionId,
                WebSocketConfig.BROKER_DEST_PREFIX_USER,
                response,
                WebSocketUtils.createHeaders(sessionId)
        );

    }
}
