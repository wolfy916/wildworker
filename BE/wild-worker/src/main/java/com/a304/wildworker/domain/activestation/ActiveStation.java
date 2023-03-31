package com.a304.wildworker.domain.activestation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ActiveStation {

    private final Long id;
    private final Map<Long, Long> subscribers = new ConcurrentHashMap<>();
    
    public void subscribe(Long userId) {
        subscribers.put(userId, userId);
    }

    public void unsubscribe(Long userId) {
        subscribers.remove(userId);
    }

}
