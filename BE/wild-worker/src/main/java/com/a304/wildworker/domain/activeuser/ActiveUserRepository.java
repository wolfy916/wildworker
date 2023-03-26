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
