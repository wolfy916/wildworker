package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.MatchDeleteEvent;
import com.a304.wildworker.service.ActiveUserService;
import com.a304.wildworker.service.MatchService;
import com.a304.wildworker.service.MiniGameLogService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeleteMatchEventHandler {

    private final ActiveUserService activeUserService;
    private final MatchService matchService;
    private final MiniGameLogService miniGameLogService;

    @EventListener
    public void deleteFromRepository(MatchDeleteEvent event) {
        log.info("event occur: DeleteMatchEvent - deleteFromRepository: {}", event);
        matchService.deleteMatch(event.getMatch().getId());
    }

    @EventListener
    public void changeActiveUserStatus(MatchDeleteEvent event) {
        log.info("event occur: DeleteMatchEvent - changeActiveUserStatus: {}", event);
        List<User> users = event.getMatch().getUsers();
        users.forEach(user -> {
            ActiveUser activeUser = activeUserService.getActiveUser(user.getId());
            activeUser.setCurrentMatchId(null);
        });
    }

    @EventListener
    public void saveLog(MatchDeleteEvent event) {
        log.info("event occur: DeleteMatchEvent - saveLog: {}", event);
        miniGameLogService.saveLog(event.getMatch());
    }
}
