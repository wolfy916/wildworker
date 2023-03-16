package com.a304.wildworker.domain;

import com.a304.wildworker.domain.entity.User;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2Attribute {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String name;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String nameAttributeKey, String name,
            String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuth2Attribute of(String provider, String attributeKey,
            Map<String, Object> attributes) {
        switch (provider) {
            case "kakao":
                return ofKakao("of", attributes);
            default:
                throw new RuntimeException();
        }
    }

    private static OAuth2Attribute ofKakao(String nameAttributeKey,
            Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .nameAttributeKey(nameAttributeKey)
                .attributes(kakaoAccount)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", nameAttributeKey);
        map.put("key", nameAttributeKey);
        map.put("name", name);
        map.put("email", email);
        return map;
    }

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setRole(Role.USER);
        return user;
    }
}