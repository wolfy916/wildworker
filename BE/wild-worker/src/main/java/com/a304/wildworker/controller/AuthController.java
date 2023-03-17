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

    //    private final KakaoAPI kakaoAPI;
//
//    @GetMapping("/auth/logout")
//    public void logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        log.info("GET:/auth/logout: {}", session);
//        if (session == null) {
//            throw new RuntimeException();   //TODO
//        }
//
//        String accessToken = (String) session.getAttribute("access_token");
//        if (accessToken != null && !"".equals(accessToken)) {
//            kakaoAPI.logout(accessToken);
//            log.info("logout success: {}", accessToken);
//        }
//        session.removeAttribute("access_token");
//        session.removeAttribute("user");
//        session.invalidate();
//    }
//
//    @GetMapping("/login/oauth2/code/kakao")
//    public ResponseEntity codeKakao(@RequestParam String code) {
//        log.info("code kakao: {}", code);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "localhost:8080/api/v1/home.html");    //TODO. change url
//        return ResponseEntity.ok().headers(headers).body(code);
//    }
}
