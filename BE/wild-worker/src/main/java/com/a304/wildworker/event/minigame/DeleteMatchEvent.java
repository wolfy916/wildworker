package com.a304.wildworker.event.minigame;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.Getter;

/**
 * Match 삭제 이벤트 (게임 취소, 게임 종료)
 */
@Getter
public class DeleteMatchEvent implements DomainEvent {

    protected Match match;

    public DeleteMatchEvent(Match match) {
        this.match = match;
    }
}
