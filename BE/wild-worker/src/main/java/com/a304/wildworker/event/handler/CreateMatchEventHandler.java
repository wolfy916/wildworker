package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.event.MatchingSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateMatchEventHandler {

    //    private final SimpMessagingTemplate messagingTemplate;
    private final MatchRepository matchRepository;

    @EventListener
    public void insertMatchRepository(MatchingSuccessEvent event) {
        log.info("MatchingSuccessEvent raise: {}", event.getMatch().getId());
        matchRepository.save(event.getMatch());
    }

    @EventListener
    public void sendMessage(MatchingSuccessEvent event) {
        Match match = event.getMatch();
        //TODO: send to users
    }
}
