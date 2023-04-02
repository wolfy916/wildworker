package com.a304.wildworker.domain.activestation;

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
    private final Map<Long, Long> investors = new ConcurrentHashMap<>();
    private final AtomicLong prevCommission = new AtomicLong(0L);

    public void subscribe(Long userId) {
        subscribers.put(userId, userId);
    }

    public void unsubscribe(Long userId) {
        subscribers.remove(userId);
    }

    public void invest(Long userId, Long amount) {
        investors.put(userId, investors.getOrDefault(userId, 0L) + amount);
    }

}
