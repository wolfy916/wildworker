package com.a304.wildworker.domain.common;

public enum TitleShowType {
    DOMINATOR,
    TITLE;

    private static final TitleShowType[] VALUES = values();

    public static TitleShowType fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }
}
