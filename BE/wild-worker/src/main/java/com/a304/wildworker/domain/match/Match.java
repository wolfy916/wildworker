package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.common.MiniGameType;
import com.a304.wildworker.domain.user.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Match {

    protected final List<User> users;
    protected MiniGameType miniGameType;

    public abstract int getCost();

    public abstract int getRunCost();

}
