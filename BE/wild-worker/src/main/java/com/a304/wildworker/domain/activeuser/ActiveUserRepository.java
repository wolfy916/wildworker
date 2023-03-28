package com.a304.wildworker.domain.activeuser;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUserRepository {

    private ConcurrentHashMap<Long, ActiveUser> activeUserMap;    //접속 중인 전체 사용자

    public ActiveUserRepository() {
        activeUserMap = new ConcurrentHashMap<>();
    }

    public Optional<ActiveUser> findById(Long id) {
        return Optional.ofNullable(activeUserMap.get(id));
    }

    public ActiveUser save(ActiveUser activeUser) {
        activeUserMap.put(activeUser.getUserId(), activeUser);
        return activeUserMap.get(activeUser.getUserId());
    }

    public void deleteById(Long id) {
        activeUserMap.remove(id);
    }

}
