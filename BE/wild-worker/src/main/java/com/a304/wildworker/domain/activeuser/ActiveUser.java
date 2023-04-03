package com.a304.wildworker.domain.activeuser;

import com.a304.wildworker.event.SetCoolTimeEvent;
import com.a304.wildworker.event.common.Events;
import java.security.Principal;
import java.util.concurrent.ScheduledFuture;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ActiveUser implements Principal {

    private final Long userId;    //유저 id
    private String websocketSessionId;  // 웹소켓 세션 id
    private Long stationId; // 현재 역 id
    private int direction;  // 지하철 이동 방향
    private boolean matchable;  // 미니게임 가능 여부
    private ScheduledFuture<?> coolTime;        //스케줄된 쿨타임 작업 상태(null: 쿨타임 아님)
    private boolean subscribed;     //현재 역 구독 여부

    public ActiveUser(Long userId) {
        this.userId = userId;
        this.websocketSessionId = null;
        this.stationId = -1L;
        this.direction = 1;
        this.matchable = false;
        this.subscribed = false;
    }

    @Override
    public String getName() {
        return String.valueOf(this.userId);
    }

    /**
     * 미니게임 가능 여부 변경 (호출 시, 반드시 메소드에 @EventPublish annotation 사용)
     *
     * @param matchable 매칭 가능 여부
     */
    public void setMatchable(boolean matchable) {
        this.matchable = matchable;
        if (!matchable) {
            resetCoolTime();
        } else {
            raiseSetCoolTimeEvent();
        }
    }

    /**
     * 역 구독 여부 변경 (호출 시, 반드시 메소드에 @EventPublish annotation 사용)
     *
     * @param subscribed 구독 여부
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
        if (!subscribed) {
            resetCoolTime();
        } else {
            this.matchable = true;
            raiseSetCoolTimeEvent();
        }
    }

    /**
     * 쿨타임 작업 변경. 쿨타임 종료시, null
     *
     * @param coolTime 스케줄된 쿨타임 작업의 상태
     */
    public void setCoolTime(ScheduledFuture<?> coolTime) {
        this.coolTime = coolTime;
    }

    /**
     * 쿨타임 타이머 초기화
     */
    private void resetCoolTime() {
        if (coolTime != null) {
            coolTime.cancel(false);
            coolTime = null;
        }
    }

    /**
     * 조건에 만족하면 SetCoolTimeEvent 발행
     */
    private void raiseSetCoolTimeEvent() {
        if (matchable && subscribed && coolTime == null) {
            Events.raise(SetCoolTimeEvent.of(this));
        }
    }

}
