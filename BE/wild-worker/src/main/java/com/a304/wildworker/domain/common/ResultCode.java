package com.a304.wildworker.domain.common;

public enum ResultCode {
    NONE,
    WIN_USER1,
    WIN_USER2,
    DRAW;

    private static final ResultCode[] VALUES = values();

    public static ResultCode fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }

}


