package com.a304.wildworker.domain.activeuser;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUserRepository {

    private Map<Long, ActiveUser> activeUserMap;    //접속 중인 전체 사용자

    public ActiveUserRepository() {
        activeUserMap = new ConcurrentHashMap<>();
    }

    public Optional<ActiveUser> findById(Long id) {
        return Optional.ofNullable(activeUserMap.get(id));
    }

    public Collection<ActiveUser> findAll() {
        return activeUserMap.values();
    }

    public ActiveUser save(ActiveUser activeUser) {
        activeUserMap.put(activeUser.getUserId(), activeUser);
        return activeUser;
    }

    public void deleteById(Long id) {
        activeUserMap.remove(id);
    }

}
