package com.a304.wildworker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
    private UserResponse user;
}
