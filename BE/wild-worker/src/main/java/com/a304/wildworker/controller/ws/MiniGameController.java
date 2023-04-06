package com.a304.wildworker.controller.ws;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.dto.request.MatchSelectRequest;
import com.a304.wildworker.dto.request.MiniGameProgressRequest;
import com.a304.wildworker.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
@MessageMapping("/minigame")
public class MiniGameController {

    private final MatchService matchService;

    @MessageMapping("/select")
    public void select(@Header("simpUser") ActiveUser user, MatchSelectRequest select) {
        log.info("/mingame/select: {}", select.isDuel());
        matchService.select(user, select);
    }

    @MessageMapping("/progress")
    public void personalProgress(@Header("simpUser") ActiveUser user,
            @Payload MiniGameProgressRequest request) {
        log.info("/minigame/progress: {}", request.getResult());
        matchService.personalProgress(user, request);
    }
}
