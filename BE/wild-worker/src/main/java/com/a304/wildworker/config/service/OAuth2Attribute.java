package com.a304.wildworker.config.service;

import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.user.User;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attribute {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String email;
    private String name;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String nameAttributeKey, String name,
            String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
        this.email = email;
    }

    public static OAuth2Attribute of(String provider, String attributeKey,
            Map<String, Object> attributes) {
        if (provider.equals("kakao")) {
            return ofKakao(attributeKey, attributes);
        }
        throw new RuntimeException();
    }

    private static OAuth2Attribute ofKakao(String nameAttributeKey,
            Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
//                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey(nameAttributeKey)
                .attributes(attributes)
                .build();
    }

    public User toEntity() {
        //TODO: create wallet
        User user = new User(email, Role.ROLE_USER, UUID.randomUUID().toString());
        return user;
    }
}