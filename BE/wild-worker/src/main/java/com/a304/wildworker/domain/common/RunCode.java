package com.a304.wildworker.domain.common;

public enum RunCode {
    NONE,
    USER1,
    USER2,
    ALL;

    private static final RunCode[] VALUES = values();

    public static RunCode fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }
}
