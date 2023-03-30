package com.a304.wildworker;

import com.a304.wildworker.event.common.DomainEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import org.springframework.stereotype.Service;

@Service
public class TestTestService {

    @EventPublish
    private void callEventPublish(DomainEvent event) {
        Events.raise(event);
    }

    @EventPublish
    public void eventRaise(DomainEvent event) {
        Events.raise(event);
    }
}
