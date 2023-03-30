package com.a304.wildworker.event;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class MatchableChangeEvent implements DomainEvent {

    private final ActiveUser activeUser;

}
