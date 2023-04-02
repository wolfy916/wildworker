package com.a304.wildworker.domain.activestation;

import com.a304.wildworker.domain.user.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActiveStation {

    private final Long id;
    private final Map<Long, Long> subscribers = new ConcurrentHashMap<>();
    private final Map<User, Long> investors = new ConcurrentHashMap<>();
    private final AtomicLong prevCommission = new AtomicLong(0L);

    public void subscribe(Long userId) {
        subscribers.put(userId, userId);
    }

    public void unsubscribe(Long userId) {
        subscribers.remove(userId);
    }

    public void invest(User user, Long amount) {
        investors.put(user, investors.getOrDefault(user, 0L) + amount);
    }

}
