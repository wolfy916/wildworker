package com.a304.wildworker.controller;

import com.a304.wildworker.domain.KakaoAPI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OAuthController {

    private final KakaoAPI kakaoAPI;

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        log.info("/user/logout");
        HttpSession session = request.getSession(false);
        String accessToken = (String) session.getAttribute("access_token");

        if (accessToken != null && !"".equals(accessToken)) {
            kakaoAPI.logout(accessToken);
            session.removeAttribute("access_token");
            session.removeAttribute("user");
            session.invalidate();

            log.info("logout success");
        }

        return "redirect:/index.html";
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity codeKakao(@RequestParam String code) {
        log.info("code kakao: {}", code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "localhost:8080/api/v1/home.html");    //TODO. change url
        return ResponseEntity.ok().headers(headers).body(code);
    }
}
