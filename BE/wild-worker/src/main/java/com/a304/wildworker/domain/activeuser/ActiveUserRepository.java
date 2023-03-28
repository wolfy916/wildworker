package com.a304.wildworker.domain.activeuser;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUserRepository {

    private ConcurrentHashMap<Long, ActiveUser> activeUserMap2;    //접속 중인 전체 사용자
    private ConcurrentHashMap<String, ActiveUser> activeUserMap;    //접속 중인 전체 사용자

    public ActiveUserRepository() {

        activeUserMap = new ConcurrentHashMap<>();
        activeUserMap2 = new ConcurrentHashMap<>();
    }

    public Optional<ActiveUser> findById(Long id) {
        return Optional.ofNullable(activeUserMap2.get(id));
    }

    public ActiveUser save(ActiveUser activeUser) {
        activeUserMap2.put(activeUser.getUserId(), activeUser);
        return activeUserMap2.get(activeUser.getUserId());
    }

    public void deleteById(Long id) {
        activeUserMap2.remove(id);
    }

    /* 접속 중인 사용자 추가 or 수정 */
    public void saveActiveUser(String httpSessionId, ActiveUser activeUser) {
        activeUserMap.put(httpSessionId, activeUser);
    }

    /* 접속 중인 사용자 삭제 */
    public ActiveUser removeActiveUser(String httpSessionId) {
        return activeUserMap.remove(httpSessionId);
    }

    /* httpSessionId로 접속 중인 사용자 정보 반환 */
    public ActiveUser getActiveUser(String httpSessionId) {
        return activeUserMap.get(httpSessionId);
    }

}
