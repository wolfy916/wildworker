package com.a304.wildworker.config.service;


import com.a304.wildworker.common.Constants;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final String clientUrl;

    public CustomLoginSuccessHandler(@Value("${url.client}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        log.info("login handler");
        HttpSession session = request.getSession();
        response.setHeader(Constants.SET_COOKIE,
                generateCookie(Constants.JSESSIONID, session.getId()).toString());
        String redirectUrl = clientUrl + "/main";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private ResponseCookie generateCookie(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
//                .sameSite("None")
                .path("/")    //TODO: get context-path
                .build();

    }
}