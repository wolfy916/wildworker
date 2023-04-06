package com.a304.wildworker.event.handler;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.event.MiniGameStartEvent;
import com.a304.wildworker.service.EventService;
import com.a304.wildworker.service.MiniGameService;
import com.a304.wildworker.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MiniGameStartEventHandler {

    private final MiniGameService miniGameService;
    private final ScheduleService scheduleService;
    private final EventService eventService;

    @Order(1)
    @EventListener
    public void createMiniGame(MiniGameStartEvent event) {
        Match match = event.getMatch();
        MiniGame miniGame = miniGameService.createMiniGame();
        match.setMiniGame(miniGame);
    }

    @Order(2)
    @EventListener
    public void setReceiveTimeLimit(MiniGameStartEvent event) {
        Match match = event.getMatch();
        MiniGameCode miniGameCode = match.getMiniGame().getCode();
        scheduleService.scheduleWithDelay(() -> eventService.endMiniGameProgressLimitTime(match),
                Constants.SELECTING_DELAY_TIME
                        + Constants.SELECTING_DELAY_TIME
                        + miniGameCode.getTimeLimit()
                        + Constants.SELECTING_DELAY_TIME);
    }

}
