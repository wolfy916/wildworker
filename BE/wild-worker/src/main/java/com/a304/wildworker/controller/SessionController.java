package com.a304.wildworker.controller;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.dto.response.LoginResponse;
import com.a304.wildworker.service.UserService;
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

    private final UserService userService;
    private final ActiveUserRepository activeUserRepository;

    @GetMapping("/")
    public ResponseEntity<LoginResponse> index(HttpServletRequest request) {
        log.info("/");
        HttpSession httpSession = request.getSession();

        SessionUser user = (SessionUser) Optional.of(
                httpSession.getAttribute(Constants.SESSION_NAME_USER)).orElseThrow();
        String accessToken = Optional.of(
                        httpSession.getAttribute(Constants.SESSION_NAME_ACCESS_TOKEN))
                .orElseThrow().toString();
        log.info("- user: {}", user);
        log.info("- accessToken: {}", accessToken);

        //접속중인 사용자에 추가
        long userId = userService.getUserId(user.getEmail());
        activeUserRepository.saveActiveUser(httpSession.getId(), new ActiveUser(userId));

        LoginResponse response = new LoginResponse(accessToken,
                userService.getUser(user.getEmail()));
        return ResponseEntity.ok(response);
    }
}