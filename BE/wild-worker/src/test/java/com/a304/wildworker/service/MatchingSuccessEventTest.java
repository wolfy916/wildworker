package com.a304.wildworker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.dummy.DummyEvent;
import com.a304.wildworker.dummy.TestEventService;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.handler.CreateMatchEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
@ComponentScan(basePackages = {"com.a304.wildworker.dummy.TestEventService"})
public class MatchingSuccessEventTest {

    @Autowired
    ApplicationEvents events;
    MatchRepository matchRepository;
    CreateMatchEventHandler eventHandler;
    @Autowired
    TestEventService testService;

    @BeforeEach
    void setUp() {
        matchRepository = new MatchRepository();
        eventHandler = new CreateMatchEventHandler(null, matchRepository);
    }


    @Test
    @DisplayName("이벤트 발행 테스트")
    public void testEventRaise() {
        MatchingSuccessEvent event = DummyEvent.getMatchingSuccessEvent();
        testService.eventRaise(event);
        int count = (int) events.stream(MatchingSuccessEvent.class).count();
        assertEquals(1, count);
    }

    @Test
    @DisplayName("MatchingSuccessEvent 발생시, MatchRepository에 Match 추가")
    public void testMatchingSuccessEventRaise() {
        MatchingSuccessEvent event = DummyEvent.getMatchingSuccessEvent();

        eventHandler.insertMatchRepository(event);

        int count = matchRepository.getMatchs().size();
        assertEquals(1, count);
    }
}
