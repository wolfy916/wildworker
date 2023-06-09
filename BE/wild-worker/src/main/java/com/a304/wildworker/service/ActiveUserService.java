package com.a304.wildworker.service;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.dto.request.MainPageRequest;
import com.a304.wildworker.event.common.EventPublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActiveUserService {

    private final ActiveUserRepository activeUserRepository;

    public String getSessionIdById(long id) {
        ActiveUser activeUser = this.getActiveUser(id);
        if (activeUser == null) {
            return null;
        }
        return activeUser.getWebsocketSessionId();
    }

    public ActiveUser getActiveUser(long id) {
        return activeUserRepository.findById(id).orElse(null);
    }

    @EventPublish
    public void setMatchableByMainPage(ActiveUser user, MainPageRequest mainPageRequest) {
        if (mainPageRequest.isMainPage()) {
            user.setMatchable(true);
        } else {
            user.setMatchable(false);
        }
    }
}
