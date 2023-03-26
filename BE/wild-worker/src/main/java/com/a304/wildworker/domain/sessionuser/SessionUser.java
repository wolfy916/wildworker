package com.a304.wildworker.domain.sessionuser;

import com.a304.wildworker.domain.user.User;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SessionUser implements Serializable {

    private final String email;
//    private final String name;
//    private final Role role;

    public SessionUser(User user) {
        this.email = user.getEmail();
//        this.name = user.getName();
//        this.role = user.getRole();
    }
}
