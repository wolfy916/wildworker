package com.a304.wildworker.event.handler;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.service.ActiveUserService;
import com.a304.wildworker.service.EventService;
import com.a304.wildworker.service.MatchService;
import com.a304.wildworker.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingSuccessEventHandler {

    private final MatchService matchService;
    private final ScheduleService scheduleService;
    private final ActiveUserService activeUserService;
    private final EventService eventService;

    @Order(1)
    @EventListener
    public void changeActiveUserStatus(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - change User Status: {}", event);
        Match match = event.getMatch();
        List<User> users = match.getUsers();
        users.forEach(user -> {
            ActiveUser activeUser = activeUserService.getActiveUser(user.getId());
            activeUser.setCurrentMatchId(match.getId());
        });
    }

    @Order(2)
    @EventListener
    public void insertToRepository(MatchingSuccessEvent event) {
        log.debug("event occur: MatchingSuccessEvent - insert to MatchRepo: {}", event);
        matchService.insertMatch(event.getMatch());
    }


    @Order(4)
    @EventListener
    public void setSelectingLimitTime(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - setReceiveCoolTime: {}", event);
        Match match = event.getMatch();

        scheduleService.scheduleWithDelay(
                () -> eventService.endMatchSelectLimitTime(match),
                match.getTimeLimitSec() + Constants.SELECTING_DELAY_TIME);
    }
}
