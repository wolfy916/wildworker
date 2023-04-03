package com.a304.wildworker.domain.common;

public enum TitleCode {
    RISK_TAKER(1),
    RUNNER(2),
    POOR(3),
    RICH(4),
    LOOSER(5),
    WINNER(6);

    private final int id;

    TitleCode(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
