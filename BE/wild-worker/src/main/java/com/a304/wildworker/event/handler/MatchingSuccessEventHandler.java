package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.event.MatchingSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingSuccessEventHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final MatchRepository matchRepository;

    @EventListener
    public void insertMatchRepository(MatchingSuccessEvent event) {
        log.info("MatchingSuccessEvent raise: {}", event.getMatch().getId());
        matchRepository.save(event.getMatch());
    }

    @EventListener
    public void changeUserStatus(MatchingSuccessEvent event) {
        log.info("MatchingSuccessEvent raise: {}", event.getMatch().getId());
        Match match = event.getMatch();
        for (ActiveUser activeUser :
                match.getUsers()) {
            activeUser.setMatchable(false);
        }
    }

    @EventListener
    public void sendMessage(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent: {}", event);
        Match match = event.getMatch();
        //TODO: send to users
    }

}
