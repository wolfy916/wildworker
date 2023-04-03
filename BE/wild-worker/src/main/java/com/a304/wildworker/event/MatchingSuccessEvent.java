package com.a304.wildworker.event;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class MatchingSuccessEvent implements DomainEvent {

    private final Match match;
}
