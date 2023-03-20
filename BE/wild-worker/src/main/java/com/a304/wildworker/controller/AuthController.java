package com.a304.wildworker.controller;

import com.a304.wildworker.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity login() {
        HttpHeaders headers = new HttpHeaders();
        String path = "/oauth2/authorization/kakao";
        headers.add("Location", Constants.URI + path);    //TODO. change url

        return ResponseEntity.ok().headers(headers).build();
    }
}
