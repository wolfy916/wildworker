package com.a304.wildworker.domain.match.strategy;

import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.user.User;

public interface LeagueStrategy {
    League getLeague(User users);
}
