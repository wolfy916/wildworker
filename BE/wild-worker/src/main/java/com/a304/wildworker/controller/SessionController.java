package com.a304.wildworker.controller;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.dto.response.LoginResponse;
import com.a304.wildworker.dto.response.UserResponse;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SessionController {

    @GetMapping("/")
    public ResponseEntity<LoginResponse> index(HttpServletRequest request) {
        log.info("session controller");
        HttpSession httpSession = request.getSession();

        String user = Optional.ofNullable(httpSession.getAttribute(Constants.USER))
                .orElse("")
                .toString();
        String accessToken = Optional.ofNullable(httpSession.getAttribute(Constants.ACCESS_TOKEN))
                .orElse("null").toString();
        log.info("- user: {}", user);
        log.info("- accessToken: {}", httpSession.getAttribute(Constants.ACCESS_TOKEN));
        return ResponseEntity.ok(new LoginResponse(accessToken, new UserResponse((user))));
    }
}