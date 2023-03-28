package com.a304.wildworker.domain.activeuser;

import java.security.Principal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ActiveUser implements Principal {

    private Long userId;    //유저 id
    private String websocketSessionId;  // 웹소켓 세션 id
    private Long stationId; // 현재 역 id
    private int direction;  // 지하철 이동 방향
    private boolean matchable;  // 미니게임 가능 여부

    public ActiveUser(Long userId) {
        this.userId = userId;
        this.websocketSessionId = null;
        this.stationId = -1L;
        this.direction = 1;
        this.matchable = false;
    }

    @Override
    public String getName() {
        return String.valueOf(this.userId);
    }
}
