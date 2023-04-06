package com.a304.wildworker.event;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class MatchSelectEndEvent implements DomainEvent {

    private Match match;
}
