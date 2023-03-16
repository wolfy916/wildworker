package com.a304.wildworker.controller;

import com.a304.wildworker.domain.SessionUser;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SessionController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        log.info("session controller");
        HttpSession httpSession = request.getSession();

        String user = Optional.ofNullable(httpSession.getAttribute("user")).orElse("null").toString();
        String accessToken = Optional.ofNullable(httpSession.getAttribute("access_token")).orElse("null").toString();
        log.info("user: {}", user);
        log.info("accessToken: {}", accessToken);
        return "hello: " + user;
    }
}