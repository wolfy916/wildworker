package com.a304.wildworker.exception.handler;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.config.WebSocketConfig;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.exception.base.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<?> handleRestException(CustomException e) {
        log.info("exception2: {}", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    @MessageExceptionHandler
    public void handleWSException(MessageHandlingException e,
            @Header("simpSessionId") String sessionId) {
        CustomException customException = (CustomException) e.getCause();
        log.info("WS Exception: {} :from {}", e.getMessage(), sessionId);
        messagingTemplate.convertAndSendToUser(sessionId,
                WebSocketConfig.BROKER_DEST_PREFIX_USER,
                WSBaseResponse.exception(customException),
                WebSocketUtils.createHeaders(sessionId));
    }

    @MessageExceptionHandler
    public void handleWSException(CustomException e,
            @Header("simpSessionId") String sessionId) {
        log.info("WS Exception: {} :from {}", e.getMessage(), sessionId);
        messagingTemplate.convertAndSendToUser(sessionId,
                WebSocketConfig.BROKER_DEST_PREFIX_USER,
                WSBaseResponse.exception(e),
                WebSocketUtils.createHeaders(sessionId));
    }

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser("/queue")
    public WSBaseResponse<?> handleArgumentNotValidException(Exception e) {
        return WSBaseResponse.exception(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
