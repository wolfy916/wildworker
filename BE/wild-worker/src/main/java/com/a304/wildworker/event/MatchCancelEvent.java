package com.a304.wildworker.event;

import com.a304.wildworker.domain.match.Match;

public class MatchCancelEvent extends DeleteMatchEvent {

    private MatchCancelEvent(Match match) {
        super(match);
    }

    public static MatchCancelEvent of(Match match) {
        return new MatchCancelEvent(match);
    }

}
