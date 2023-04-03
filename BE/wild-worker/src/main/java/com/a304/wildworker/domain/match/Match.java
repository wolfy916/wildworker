package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.MiniGameType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Match {

    protected final String id;
    protected final List<ActiveUser> users;
    protected MiniGameType miniGameType;

    protected Match(String id, List<ActiveUser> users) {
        this.id = id;
        this.users = users;
    }

    public abstract int getCost();

    public abstract int getRunCost();

}
