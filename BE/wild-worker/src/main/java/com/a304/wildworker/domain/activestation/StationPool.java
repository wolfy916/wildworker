package com.a304.wildworker.domain.activestation;

import com.a304.wildworker.event.PoolChangeEvent;
import com.a304.wildworker.event.common.Events;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import lombok.Getter;

@Getter
public class StationPool {

    private final Long id;
    private final Queue<Long> pool;

    public StationPool(Long id) {
        this.id = id;
        this.pool = new ConcurrentLinkedDeque<>();
    }

    public void insert(Long userId) {
        //user must subscribe and matchable
        if (!pool.contains(userId)) {
            pool.offer(userId);
        }
        Events.raise(PoolChangeEvent.of(this));
    }

    public void removeById(Long userId) {
        pool.remove(userId);
    }
}
