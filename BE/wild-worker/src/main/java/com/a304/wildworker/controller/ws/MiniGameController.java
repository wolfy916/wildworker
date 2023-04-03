package com.a304.wildworker.controller.ws;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.dto.request.MatchSelectRequest;
import com.a304.wildworker.dto.request.MiniGameProgressRequest;
import com.a304.wildworker.service.MiniGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;


@Slf4j
@RequiredArgsConstructor
@Controller
@MessageMapping("/minigame")
public class MiniGameController {

    private final MiniGameService miniGameService;

    @MessageMapping("/select")
    public void select(@Header("simpUser") ActiveUser user, @Payload MatchSelectRequest request) {
        miniGameService.select(user, request);
    }

    @MessageMapping("/progress")
    public void progress(@Header("simpUser") ActiveUser user,
            @Payload MiniGameProgressRequest request) {
        miniGameService.progress(user, request);
    }
}
