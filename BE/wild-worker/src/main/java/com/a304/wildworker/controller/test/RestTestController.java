package com.a304.wildworker.controller.test;

import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class RestTestController {

    private final TestService service;

    @GetMapping
    public void test() {
        Match match = new DefaultMatch(null, 0);
//        service.publishEvent(match);
        service.raiseEvent(match);
        log.info("test: {}", match.getId());
    }

    @Transactional
    @EventPublish
    private void func(Match match) {
        Events.raise(MatchingSuccessEvent.of(match));
    }
}
