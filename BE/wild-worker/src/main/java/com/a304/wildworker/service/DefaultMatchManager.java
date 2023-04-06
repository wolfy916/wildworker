package com.a304.wildworker.service;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.common.MatchStatus;
import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.strategy.DefaultLeagueStrategy;
import com.a304.wildworker.domain.match.strategy.LeagueStrategy;
import com.a304.wildworker.domain.user.User;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class DefaultMatchManager {

    @Setter
    private static int MAX_USER = 2;
    @Setter
    private LeagueStrategy leagueStrategy = new DefaultLeagueStrategy();

    public void createMatches(Stream<User> pool, ActiveStation activeStation) {
        Map<League, Queue<User>> byLeague =
                pool.collect(Collectors.groupingBy(this::getLeague,
                        Collectors.toCollection(LinkedList::new)));

        byLeague.remove(League.NONE);
        byLeague.forEach(((league, users) -> {
            while (users.size() >= MAX_USER) {
                List<User> matchUsers = new ArrayList<>(MAX_USER);
                for (int i = 0; i < MAX_USER; i++) {
                    matchUsers.add(users.poll());
                }

                Match match = new DefaultMatch(matchUsers, activeStation.getId(), league);
                match.changeStatus(MatchStatus.MATCHING);
            }
        }));
    }

    private League getLeague(User user) {
        return leagueStrategy.getLeague(user);
    }
}
