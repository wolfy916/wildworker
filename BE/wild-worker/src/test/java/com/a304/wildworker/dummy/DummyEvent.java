package com.a304.wildworker.dummy;

import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.MatchingSuccessEvent;

public class DummyEvent {

    public static MatchingSuccessEvent getMatchingSuccessEvent() {
        Match match = new DefaultMatch(null, 0, League.LOW);
        return MatchingSuccessEvent.of(match);
    }


}
