package com.a304.wildworker.auth;


import com.a304.wildworker.common.Constants;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final String clientUrl;
    private final String redirectPath = "/redirect/login";

    public CustomLoginSuccessHandler(@Value("${url.client}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        log.info("login handler");
        HttpSession session = request.getSession();

        // 메인으로 리다이렉트
        String prevPage = Optional.ofNullable(
                        session.getAttribute(Constants.SESSION_NAME_PREV_PAGE))
                .orElse(clientUrl).toString();
        session.removeAttribute(Constants.SESSION_NAME_PREV_PAGE);
        String redirectUrl = UriComponentsBuilder.fromHttpUrl(prevPage).replacePath(redirectPath)
                .build()
                .toString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}