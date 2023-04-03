package com.a304.wildworker.domain.common;

public enum League {
    NONE,       //리그 배정 없음
    LOW,
    MIDDLE,
    HIGH,
    TOP;

    private static final League[] VALUES = values();

    public static League fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }

}
