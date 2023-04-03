package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.DeleteMatchEvent;
import com.a304.wildworker.service.ActiveUserService;
import com.a304.wildworker.service.MiniGameLogService;
import com.a304.wildworker.service.MiniGameService;
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
    private final MiniGameService miniGameService;
    private final MiniGameLogService miniGameLogService;

    @EventListener
    public void deleteFromRepository(DeleteMatchEvent event) {
        log.info("event occur: DeleteMatchEvent - deleteFromRepository: {}", event);
        miniGameService.deleteMatch(event.getMatch().getId());
    }

    @EventListener
    public void changeActiveUserStatus(DeleteMatchEvent event) {
        log.info("event occur: DeleteMatchEvent - changeActiveUserStatus: {}", event);
        List<User> users = event.getMatch().getUsers();
        users.forEach(user -> {
            ActiveUser activeUser = activeUserService.getActiveUser(user.getId());
            activeUser.setCurrentMatchId(null);
            activeUser.setMatchable(true);
        });
    }

    @EventListener
    public void saveLog(DeleteMatchEvent event) {
        log.info("event occur: DeleteMatchEvent - saveLog: {}", event);
        miniGameLogService.saveLog(event.getMatch());
    }
}
