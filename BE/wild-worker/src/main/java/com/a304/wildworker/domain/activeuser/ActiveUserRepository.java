package com.a304.wildworker.domain.activeuser;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUserRepository {

    private ConcurrentHashMap<Long, ActiveUser> activeUserMap;    //접속 중인 전체 사용자

    public ActiveUserRepository() {
        activeUserMap = new ConcurrentHashMap<>();
    }

    /* 접속 중인 사용자 추가 or 수정 */
    public void saveActiveUser(Long userId, ActiveUser activeUser) {
        activeUserMap.put(userId, activeUser);
    }

    /* 접속 중인 사용자 삭제 */
    public ActiveUser removeActiveUser(Long userId) {
        return activeUserMap.remove(userId);
    }

    /* httpSessionId로 접속 중인 사용자 정보 반환 */
    public ActiveUser getActiveUser(long userId) {
        return activeUserMap.get(userId);
    }

}
