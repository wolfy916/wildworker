package com.a304.wildworker.config.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.service.WalletProvider;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
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
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("login - loadUser");
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // Oath2 정보를 가져옴

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // 소셜 정보 가져옴
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attribute attributes = OAuth2Attribute.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User user = findOrSave(attributes);
        httpSession.setAttribute(Constants.SESSION_NAME_USER, new SessionUser(user));
        log.info("- {}: {}", Constants.SESSION_NAME_USER, user);

        String accessToken = userRequest.getAccessToken().getTokenValue();
        httpSession.setAttribute(Constants.SESSION_NAME_ACCESS_TOKEN, accessToken);
        log.info("- {}: {}", Constants.SESSION_NAME_ACCESS_TOKEN, accessToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User findOrSave(OAuth2Attribute attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .orElse(createNewUser(attributes.getEmail()));

        return userRepository.save(user);
    }

    private User createNewUser(String email) {
        User user = new User(email, Role.ROLE_USER);
        try {
            user.setWallet(WalletProvider.createUserWallet(user.getWalletPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}