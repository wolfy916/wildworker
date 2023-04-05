package com.a304.wildworker.domain.activeuser;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.event.SetMatchCoolTimeEvent;
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
    private boolean subscribed;     //현재 역 구독 여부
    private boolean matchable;  // 미니게임 가능 여부
    private ScheduledFuture<?> coolTime;        //스케줄된 쿨타임 작업 상태(null: 쿨타임 아님)
    private String currentMatchId;   //진행중인 미니게임 id
    private ActiveStation activeStation;    //현재 등록된 Station Pool

    public ActiveUser(Long userId) {
        this.userId = userId;
        this.websocketSessionId = null;
        this.stationId = -1L;
        this.direction = 1;
        this.matchable = false;
        this.subscribed = false;
        this.coolTime = null;
        this.currentMatchId = null;
        this.activeStation = null;
    }

    @Override
    public String getName() {
        return String.valueOf(this.userId);
    }

    public void setCurrentMatchId(String matchId) {
        if (currentMatchId != null && matchId != null) {
            throw new RuntimeException("이미 게임 중인 플레이어입니다.");    //TODO
        }
        this.currentMatchId = matchId;
        setOrResetCoolTime();
    }

    /**
     * 미니게임 가능 여부 변경 (호출 시, 반드시 메소드에 @EventPublish annotation 사용)
     *
     * @param matchable 매칭 가능 여부
     */
    public void setMatchable(boolean matchable) {
        this.matchable = matchable;
        setOrResetCoolTime();
    }

    /**
     * 역 구독 여부 변경 (호출 시, 반드시 메소드에 @EventPublish annotation 사용)
     *
     * @param subscribed 구독 여부
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
        this.matchable = subscribed;    //TODO
        setOrResetCoolTime();
    }

    public void setOrResetCoolTime() {
        if (canMatching()) {
            if (getCoolTime() == null) {
                Events.raise(SetMatchCoolTimeEvent.of(this));
            }
        } else {
            this.resetCoolTimeAndRemoveFromPool();
        }
    }


    /**
     * 쿨타임 타이머 및 매칭 pool 초기화
     */
    public void resetCoolTimeAndRemoveFromPool() {
        if (coolTime != null) {
            coolTime.cancel(false);
            coolTime = null;
        }

        if (activeStation != null) {
            activeStation.removeFromPool(userId);
            activeStation = null;
        }
    }

    /**
     * @return 미니 게임 가능 여부
     */
    public boolean canMatching() {
        return subscribed && matchable && currentMatchId == null;
    }
}
