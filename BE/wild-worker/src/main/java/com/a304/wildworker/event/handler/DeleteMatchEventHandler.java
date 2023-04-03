package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.minigame.DeleteMatchEvent;
import com.a304.wildworker.service.ActiveUserService;
import com.a304.wildworker.service.MiniGameService;
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

    @EventListener
    public void deleteFromRepository(DeleteMatchEvent event) {
        miniGameService.deleteMatch(event.getMatch().getId());
    }

    @EventListener
    public void changeActiveUserStatus(DeleteMatchEvent event) {
        var users = event.getMatch().getUsers();
        users.forEach(user -> {
            ActiveUser activeUser = activeUserService.getActiveUser(user.getId());
            activeUser.setCurrentMatchId(null);
            activeUser.setMatchable(true);
        });
    }
}
