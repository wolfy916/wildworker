package com.a304.wildworker.dummy;

import com.a304.wildworker.domain.activeuser.ActiveUser;

public class DummyActiveUser {

    public static ActiveUser getActiveUser() {
        return new ActiveUser(1L);
    }
}
