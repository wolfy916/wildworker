package com.a304.wildworker.domain.activeuser;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUserRepository {

    private ConcurrentHashMap<String, ActiveUser> activeUserMap;    //접속 중인 전체 사용자

    public ActiveUserRepository() {
        activeUserMap = new ConcurrentHashMap<>();
    }

    /* 접속 중인 사용자 추가 or 수정 */
    public void saveActiveUser(String sessionId, ActiveUser activeUser) {
        activeUserMap.put(sessionId, activeUser);
    }

    /* 접속 중인 사용자 삭제 */
    public ActiveUser removeActiveUser(String sessionId) {
        return activeUserMap.remove(sessionId);
    }

    /* sessionId로 접속 중인 사용자 정보 반환 */
    public ActiveUser getActiveUser(String sessionId) {
        return activeUserMap.get(sessionId);
    }

}
