package com.a304.wildworker.domain.activestation;

import com.a304.wildworker.event.PoolChangeEvent;
import com.a304.wildworker.event.common.Events;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActiveStation {

    private final Long id;
    private final Queue<Long> pool = new ConcurrentLinkedDeque<>();
    private final Map<Long, Long> investors = new ConcurrentHashMap<>();

    public void insertToPool(Long userId) {
        //user must subscribe and matchable
        if (!pool.contains(userId)) {
            pool.offer(userId);
        }
        Events.raise(PoolChangeEvent.of(this));
    }

    public void removeFromPool(Long userId) {
        pool.remove(userId);
    }

    public void invest(Long userId, Long amount) {
        investors.put(userId, investors.getOrDefault(userId, 0L) + amount);
    }

    public void resetInvestors() {
        investors.clear();
    }

}
