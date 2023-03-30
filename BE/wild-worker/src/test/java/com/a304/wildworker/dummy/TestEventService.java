package com.a304.wildworker.dummy;

import com.a304.wildworker.event.common.DomainEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import org.springframework.stereotype.Service;

@Service
public class TestEventService {

    private void callEventPublish(DomainEvent event) {
        Events.raise(event);
    }

    @EventPublish
    public void eventRaise(DomainEvent event) {
        callEventPublish(event);
    }

}
