package com.a304.wildworker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.DomainEvent;
import com.a304.wildworker.event.handler.CreateMatchEventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
@Service
public class MatchingSuccessEventTest {

    @Autowired
    ApplicationEvents events;
    MatchRepository matchRepository;
    CreateMatchEventHandler eventHandler;

    @BeforeEach
    void setUp() {
        this.matchRepository = new MatchRepository();
        this.eventHandler = new CreateMatchEventHandler(matchRepository);
    }

    private MatchingSuccessEvent createEvent() {
        Match match = new DefaultMatch(null, 0);
        MatchingSuccessEvent event = MatchingSuccessEvent.of(match);
        return event;
    }


    @Test
    @DisplayName("이벤트 발행 테스트")
    public void testEventRaise() {
        DomainEvent event = createEvent();
        TestTestService testService = new TestTestService();
        testService.eventRaise(event);
        int count = (int) events.stream(MatchingSuccessEvent.class).count();
        assertEquals(1, count);
    }

    @Test
    @DisplayName("MatchingSuccessEvent 발생시, MatchRepository에 Match 추가")
    public void testMatchingSuccessEventRaise() {
        Match match = new DefaultMatch(null, 0);
        MatchingSuccessEvent event = MatchingSuccessEvent.of(match);

        eventHandler.insertMatchRepository(event);

        int count = matchRepository.getMatchs().size();
        assertEquals(1, count);
    }
}
