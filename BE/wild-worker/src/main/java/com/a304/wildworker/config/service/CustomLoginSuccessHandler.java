package com.a304.wildworker.config.service;


import com.a304.wildworker.common.Constants;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        log.info("login handler");
        String referer = request.getHeader("Referer");
        log.info("-- referer: {}", referer);
        String prevPage = (String) request.getSession()
                .getAttribute(Constants.SESSION_NAME_PREV_PAGE);
        log.info("-- prevPage: {}", prevPage);
        String redirectUrl = Optional.ofNullable(referer)
                .orElse(Optional.ofNullable(prevPage).orElseThrow());
        redirectUrl += "main";     //TODO. get from config?
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}