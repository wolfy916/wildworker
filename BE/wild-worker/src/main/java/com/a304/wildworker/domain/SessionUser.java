package com.a304.wildworker.domain;

import com.a304.wildworker.domain.entity.User;
import lombok.Getter;

@Getter
public class SessionUser {

    private String email;
    private String name;

    public SessionUser(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
