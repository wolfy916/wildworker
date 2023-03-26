package com.a304.wildworker.controller.ws;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.exception.TestException;
import lombok.RequiredArgsConstructor;
import com.a304.wildworker.common.Constants;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
@MessageMapping("/test")
public class TestController {

    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/broadcast")
    @SendTo("/sub/system")
    public String test(@Header("simpSessionId") String sessionId, String message,
            SimpMessageHeaderAccessor headerAccessor) {
        String httpSession = headerAccessor.getSessionAttributes().get(Constants.KEY_SESSION_ID)
                .toString();
        String webSocketSession = headerAccessor.getSessionId();
        log.info("{}, {}, {}", Objects.requireNonNull(webSocketSession).equals(httpSession),
                webSocketSession,
                httpSession);
        String res =
                "/test/broadcast: " + message + " : " + sessionId + " : " + httpSession;
        log.info(res);
        return res;
    }


    @MessageMapping("/personal")
    @SendToUser("/queue")
    public String testPersonal(@Header("simpSessionId") String sessionId, String message) {
        String res = "/test/personal: " + message + " : " + sessionId;
        log.info(res);
        messagingTemplate.convertAndSendToUser(sessionId, "/queue", res,
                WebSocketUtils.createHeaders(sessionId));
        return res;
    }

    @MessageMapping("/exception")
    @SendToUser("/queue")
    public String testException(@Header("simpSessionId") String sessionId, String message) {
        String res = "/test/exception: " + message + " : " + sessionId;
        log.info(res);
        throw new TestException("/test/exeption");
    }

}
