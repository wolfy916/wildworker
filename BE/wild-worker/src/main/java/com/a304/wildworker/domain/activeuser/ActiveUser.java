package com.a304.wildworker.domain.activeuser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ActiveUser {

    private long userId;    // 사용자 id
    private long stationId; // 현재 역 id
    private boolean matchable;  // 미니게임 가능 여부
    private String websocketSessionId;  // 웹소켓 세션 id

    public ActiveUser(long userId, String websocketSessionId) {
        this.userId = userId;
        this.stationId = 0;
        this.matchable = true;
        this.websocketSessionId = websocketSessionId;
    }

}
