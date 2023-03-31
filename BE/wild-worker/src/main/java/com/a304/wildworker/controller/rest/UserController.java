package com.a304.wildworker.controller.rest;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.dto.response.UserResponse;
import com.a304.wildworker.exception.NotLoginException;
import com.a304.wildworker.service.UserService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request) {
        log.info("GET /user");
        HttpSession httpSession = request.getSession();

        SessionUser user = (SessionUser) Optional.of(
                        httpSession.getAttribute(Constants.SESSION_NAME_USER))
                .orElseThrow(NotLoginException::new);
        log.info("- user: {}", user);

        UserResponse response = userService.getUser(user.getEmail());
        return ResponseEntity.ok(response);
    }
}