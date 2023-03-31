package com.a304.wildworker.event;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangedBalanceEvent {

    private User user;
    private Station station;
    private TransactionType reason;
    private long changeValue;

}
