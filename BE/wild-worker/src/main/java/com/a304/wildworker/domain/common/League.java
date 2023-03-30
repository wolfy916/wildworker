package com.a304.wildworker.domain.common;

public enum League {
    LOW,
    MIDDLE,
    HIGH,
    TOP;

    private static final League[] VALUES = values();

    public static League fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }

}
