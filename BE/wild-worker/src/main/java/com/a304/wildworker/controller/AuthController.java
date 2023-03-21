package com.a304.wildworker.controller;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequest(request)
                .replacePath(request.getContextPath()).build().toString();
        String redirectURI = baseUrl + "/oauth2/authorization/kakao";
        log.info("/auth/login: {}", redirectURI);
        URI uri = URI.create(redirectURI);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(uri).build();
    }
}
