package com.a304.wildworker.controller;

import com.a304.wildworker.common.Constants;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity login() {
        log.info("/auth/login");
        String redirectURI = Constants.URI + "/oauth2/authorization/kakao";
        URI uri = URI.create(redirectURI);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(uri).build();
    }
}
