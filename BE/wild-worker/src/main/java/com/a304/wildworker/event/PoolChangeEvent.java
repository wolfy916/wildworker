package com.a304.wildworker.event;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "of")
public class PoolChangeEvent implements DomainEvent {

    private final ActiveStation activeStation;
}
