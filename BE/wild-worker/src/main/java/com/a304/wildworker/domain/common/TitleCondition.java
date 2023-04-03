package com.a304.wildworker.domain.common;

public enum TitleCondition {
    RISK_TAKER(20),
    RUNNER(20),
    POOR(0),
    RICH(100000),
    LOOSER(10),
    WINNER(10);

    private final int value;

    TitleCondition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
