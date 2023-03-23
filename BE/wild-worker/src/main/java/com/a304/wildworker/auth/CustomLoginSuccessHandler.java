package com.a304.wildworker.auth;


import com.a304.wildworker.common.Constants;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        log.info("login handler");
        String referer = request.getHeader("Referer");
        log.info("-- referer: {}", referer);
        HttpSession session = request.getSession();
        String prevPage = (String) session
                .getAttribute(Constants.SESSION_NAME_PREV_PAGE);
        log.info("-- prevPage: {}", prevPage);
        String redirectUrl = Optional.ofNullable(referer)
                .orElse(Optional.ofNullable(prevPage).orElseThrow());
        redirectUrl += "main";     //TODO. get from config?
        response.setHeader(Constants.SET_COOKIE,
                generateCookie(Constants.KEY_SESSION_ID, session.getId()).toString());
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