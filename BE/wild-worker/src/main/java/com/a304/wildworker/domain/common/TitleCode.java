package com.a304.wildworker.domain.common;

public enum TitleCode {
    RISK_TAKER(1L),
    RUNNER(2L),
    POOR(3L),
    RICH(4L),
    LOOSER(5L),
    WINNER(6L);

    private final Long id;

    TitleCode(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
