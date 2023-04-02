package com.a304.wildworker.controller.rest;

import com.a304.wildworker.domain.sessionuser.PrincipalDetails;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.dto.response.UserResponse;
import com.a304.wildworker.exception.NotLoginException;
import com.a304.wildworker.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal PrincipalDetails principal) {
        log.info("GET /user");

        SessionUser user = Optional.of(principal.getSessionUser())
                .orElseThrow(NotLoginException::new);
        log.info("- user: {}", user);

        UserResponse response = userService.getUser(user.getEmail());
        return ResponseEntity.ok(response);
    }

}