package com.a304.wildworker.domain.sessionuser;

import com.a304.wildworker.domain.user.User;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SessionUser implements Serializable {

    private final Long id;
    private final String email;

    public SessionUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

}
