package com.a304.wildworker.controller.test;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.EventPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final ApplicationEventPublisher eventPublisher;
    ActiveStation activeStation = new ActiveStation(1L);

    public void publishEvent(Match match) {
        log.info("test service: {}", match.getId());
        eventPublisher.publishEvent(MatchingSuccessEvent.of(match));
    }

    @EventPublish
    public void raiseEvent(Match match) {
        log.info("test service(raiseEvent): {}", match.getId());
//        activeStation.testMake();
//        Match match1 = new DefaultMatch(null, 0);
//        Events.raise(MatchingSuccessEvent.of(match));
    }
}
