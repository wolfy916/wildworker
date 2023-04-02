package com.a304.wildworker.event.common;

import org.springframework.context.ApplicationEventPublisher;

public class Events {

    private static final ThreadLocal<ApplicationEventPublisher> publisherLocal = new ThreadLocal<>();

    public static void raise(DomainEvent event) {
        if (event == null) {
            return;
        }

        if (publisherLocal.get() != null) {
            publisherLocal.get().publishEvent(event);
        }
    }

    static void setPublisher(ApplicationEventPublisher publisher) {
        publisherLocal.set(publisher);
    }

    static void reset() {
        publisherLocal.remove();
    }
}
