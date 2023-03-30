package com.a304.wildworker.domain.activestation;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.Events;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActiveStation {

    private final Long id;
    private final Map<Long, ActiveUser> subscribers = new ConcurrentHashMap<>();
    private final Queue<Long> pool = new ConcurrentLinkedQueue<>();

    public void subscribe(ActiveUser user) {
        subscribers.put(user.getUserId(), user);
    }

    public void unsubscribe(Long userId) {
        subscribers.remove(userId);
    }

    public void insertPool(Long userId) {
        pool.offer(userId);
        if (pool.size() > 2) {
            makeMatch();
        }
    }

    public void makeMatch() {
        List<ActiveUser> users = new ArrayList<>(2);
        while (pool.size() > 0 && users.size() < 2) {
            long id = pool.poll();
            if (!subscribers.containsKey(id)) {
                continue;
            }
            users.add(subscribers.get(id));
        }

        if (users.size() < 2) {
            pool.offer(users.get(0).getUserId());
            return;
        }

        Match match = new DefaultMatch(users, League.BRONZE.ordinal());
        Events.raise(MatchingSuccessEvent.of(match));
    }
}
