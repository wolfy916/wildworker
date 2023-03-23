package com.a304.wildworker.exception.handler;

import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.exception.custom_exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @MessageExceptionHandler(CustomException.class)
    @SendToUser("/queue")
    public WSBaseResponse<?> handleException(CustomException e) {
//        log.info("exception: {}", e.getMessage());
        return WSBaseResponse.exception(e);
    }

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser("/queue")
    public WSBaseResponse<?> handleArgumentNotValidException(Exception e) {
        return WSBaseResponse.exception(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
