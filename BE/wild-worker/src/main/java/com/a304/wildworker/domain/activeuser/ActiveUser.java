package com.a304.wildworker.domain.activeuser;

import java.security.Principal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ActiveUser implements Principal {

    private String httpSessionId;   // http 세션 id
    private String websocketSessionId;  // 웹소켓 세션 id
    private long stationId; // 현재 역 id
    private int direction;  // 지하철 이동 방향
    private boolean matchable;  // 미니게임 가능 여부

    public ActiveUser(String httpSessionId) {
        this.httpSessionId = httpSessionId;
        this.websocketSessionId = null;
        this.stationId = -1;
        this.direction = 1;
        this.matchable = true;
    }

    @Override
    public String getName() {
        return this.httpSessionId;
    }
}
