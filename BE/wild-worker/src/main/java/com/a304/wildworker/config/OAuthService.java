package com.a304.wildworker.config;

import com.a304.wildworker.domain.Role;
import com.a304.wildworker.domain.SessionUser;
import com.a304.wildworker.domain.entity.User;
import com.a304.wildworker.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String serviceName = userRequest.getClientRegistration()
                .getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registrationId = {}", serviceName);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        Map<String, Object> attributes = oAuth2User.getAttributes();

//        OAuth2Attribute oAuth2Attribute =
//                OAuth2Attribute.of(serviceName, userNameAttributeName, oAuth2User.getAttributes());
//        var memberAttribute = oAuth2Attribute.convertToMap();

        String email;

        if ("kakao".equals(serviceName)) {
            Map<String, Object> profile = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) profile.get("email");
        } else {
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        User user;
        Optional<User> optionalUser = userRepository.findByEmail(email);
        String accessToken = userRequest.getAccessToken().getTokenValue();

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setRole(Role.ROLE_USER);
            userRepository.save(user);
        }

        httpSession.setAttribute("user", new SessionUser(user));
        httpSession.setAttribute("access_token", accessToken);
        log.info("user: {}", user);
        log.info("accessToken: {}", accessToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
                , oAuth2User.getAttributes()
                , userNameAttributeName
        );
    }
}