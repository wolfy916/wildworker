package com.a304.wildworker.service;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.exception.UserNotFoundException;
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
        return activeUser.getWebsocketSessionId();
    }

    public ActiveUser getActiveUser(long id) {
        return activeUserRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
}
