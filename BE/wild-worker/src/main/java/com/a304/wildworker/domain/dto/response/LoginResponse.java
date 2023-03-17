package com.a304.wildworker.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class LoginResponse {

    /*
    *{
        "accessToken": "액세스 토큰",
        "user": {
            "name": "S2태형S2",
            "title": "쫄보",
            "coin": 1000,
            "collectedPapers": 15
        }
    }
    * */
    private String accessToken;
    private UserResponse user;
}
