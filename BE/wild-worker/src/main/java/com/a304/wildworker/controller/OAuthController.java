package com.a304.wildworker.controller;

import com.a304.wildworker.domain.KakaoAPI;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OAuthController {

    private final KakaoAPI kakaoAPI;

    @RequestMapping("/user/logout/kakao")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("access_token");

        if (accessToken != null && !"".equals(accessToken)) {
            kakaoAPI.logout(accessToken);
            session.removeAttribute("access_token");
            session.removeAttribute("user");

            log.info("logout success");
        }

        return "redirect:/";
    }

    @GetMapping("/login/oauth2/code/kakao")
    public String codeKakao(@RequestParam String code) {
        log.info("code kakao: {}", code);
        return code;
    }
}
