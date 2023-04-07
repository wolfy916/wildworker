package com.a304.wildworker.event;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class TransactionEvent implements DomainEvent {

    private TransactionType type;
    private Station station;
    private User user;
    private long value;
}
