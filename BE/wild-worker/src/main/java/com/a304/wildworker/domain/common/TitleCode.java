package com.a304.wildworker.domain.common;

public enum TitleCode {
    NONE(-1L, 0),
    RISK_TAKER(1L, 20),
    RUNNER(2L, 20),
    POOR(3L, 0),
    RICH(4L, 100000),
    LOOSER(5L, 10),
    WINNER(6L, 10);

    private final Long id;
    private final int condition;

    TitleCode(Long id, int condition) {
        this.id = id;
        this.condition = condition;
    }

    public Long getId() {
        return id;
    }

    public int getCondition() {
        return condition;
    }
}
