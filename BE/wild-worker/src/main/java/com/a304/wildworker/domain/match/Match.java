package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.common.MiniGameType;
import com.a304.wildworker.domain.user.User;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Match {

    public static final int timeLimitSec = 10;
    private static final int defaultCost = 20;
    private static final int defaultRunCost = 5;
    private final List<User> users;
    private final int league;
    private final MiniGameType miniGameType;

    public Match(User user1, User user2, int league) {
        users = List.of(user1, user2);
        this.league = league;
        miniGameType = null; //TODO: create rand
    }

    public int getCost() {
        return defaultCost * (int) (Math.pow(10, league));
    }

    public int getRunCost() {
        return defaultRunCost * (int) (Math.pow(10, league));
    }

}
