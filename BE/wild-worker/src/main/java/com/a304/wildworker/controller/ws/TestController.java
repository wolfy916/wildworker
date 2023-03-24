package com.a304.wildworker.controller.ws;

import com.a304.wildworker.common.Constants;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TestController {

    @MessageMapping("/test/broadcast")
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


    @MessageMapping("/test/personal")
    @SendToUser("/queue/noti")
    public String testPersonal(@Header("simpSessionId") String sessionId, String message) {
        String res = "/test/personal: " + message + " : " + sessionId;
        log.info(res);
        return res;
    }

}
