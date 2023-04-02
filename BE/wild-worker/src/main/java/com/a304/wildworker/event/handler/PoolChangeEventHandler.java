package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activestation.StationPool;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.strategy.DefaultLeagueStrategy;
import com.a304.wildworker.domain.match.strategy.LeagueStrategy;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.PoolChangeEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PoolChangeEventHandler {

    private final UserRepository userRepository;
    private final ActiveUserRepository activeUserRepository;
    private final LeagueStrategy leagueStrategy;

    @Autowired
    public PoolChangeEventHandler(UserRepository userRepository,
            ActiveUserRepository activeUserRepository) {
        this.userRepository = userRepository;
        this.activeUserRepository = activeUserRepository;
        this.leagueStrategy = new DefaultLeagueStrategy();
    }

    @EventPublish
    @EventListener
    public void makeMatch(PoolChangeEvent event) {
        log.info("PoolChangeEvent raise: {}", event);

        StationPool stationPool = event.getStationPool();
        Queue<Long> pool = stationPool.getPool();

        Map<League, Queue<Long>> byLeague = pool.stream()
                .map(id -> userRepository.findById(id).orElseThrow())
                .collect(Collectors.groupingBy(this::getLeague,
                        Collectors.mapping(User::getId, Collectors.toCollection(LinkedList::new))));

        byLeague.remove(League.NONE);
        byLeague.forEach(((league, users) -> {
            while (users.size() >= 2) {
                ActiveUser user1 = activeUserRepository.findById(users.poll()).orElseThrow();
                ActiveUser user2 = activeUserRepository.findById(users.poll()).orElseThrow();
                List<ActiveUser> matchUsers = List.of(user1, user2);
                Match match = new DefaultMatch(matchUsers, league);
                Events.raise(MatchingSuccessEvent.of(match));
                for (ActiveUser user :
                        matchUsers) {
                    stationPool.removeById(user.getUserId());
                }
            }
        }));
    }

    private League getLeague(User user) {
        return leagueStrategy.getLeague(user);
    }
}
