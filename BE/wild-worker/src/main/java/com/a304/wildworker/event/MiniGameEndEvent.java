package com.a304.wildworker.event;

import com.a304.wildworker.domain.match.Match;

public class MiniGameEndEvent extends MatchDeleteEvent {

    private MiniGameEndEvent(Match match) {
        super(match);
    }

    public static MiniGameEndEvent of(Match match) {
        return new MiniGameEndEvent(match);
    }

}
