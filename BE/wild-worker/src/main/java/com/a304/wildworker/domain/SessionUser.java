package com.a304.wildworker.domain;

import com.a304.wildworker.domain.entity.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SessionUser {

    private final String email;
//    private final String name;
//    private final Role role;

    public SessionUser(User user) {
        this.email = user.getEmail();
//        this.name = user.getName();
//        this.role = user.getRole();
    }
}
