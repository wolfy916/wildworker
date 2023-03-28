package com.a304.wildworker.auth;


import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.service.UserService;
import java.io.IOException;
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

    private final UserService userService;
    private final ActiveUserRepository activeUserRepository;

    private final String clientUrl;

    private final String redirectPath = "/redirect/login";

    public CustomLoginSuccessHandler(@Value("${url.client}") String clientUrl,
            UserService userService, ActiveUserRepository activeUserRepository) {
        this.clientUrl = clientUrl;
        this.userService = userService;
        this.activeUserRepository = activeUserRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        log.info("login handler");
        HttpSession session = request.getSession();

        // 메인으로 리다이렉트
        response.setHeader(Constants.SET_COOKIE,
                generateCookie(Constants.KEY_SESSION_ID, session.getId()).toString());
        String redirectUrl = clientUrl + redirectPath;
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