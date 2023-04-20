package com.a304.wildworker.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TransactionLogAppliedEvent {

    private final Long transactionId;
}
