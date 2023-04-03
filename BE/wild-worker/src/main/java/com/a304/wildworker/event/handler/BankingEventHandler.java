package com.a304.wildworker.event.handler;

import com.a304.wildworker.event.MatchSelectEndEvent;
import com.a304.wildworker.event.MiniGameEndEvent;
import com.a304.wildworker.event.MiniGameStartEvent;
import com.a304.wildworker.service.MiniGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BankingEventHandler {

    private final MiniGameService miniGameService;


    @EventListener
    public void sendRunCost(MatchSelectEndEvent event) {
        miniGameService.transactionRunCost(event.getMatch());
    }

    @EventListener
    public void sendGameCost(MiniGameStartEvent event) {
        miniGameService.transactionGameCost(event.getMatch());
    }

    @EventListener
    public void getReward(MiniGameEndEvent event) {
        miniGameService.transactionReward(event.getMatch());
    }

}
