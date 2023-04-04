package com.a304.wildworker.domain.common;

public enum CharacterType {
    MAN,
    WOMAN;

    private static final CharacterType[] VALUES = values();

    public static CharacterType fromOrdinary(int ordinary) {
        return VALUES[ordinary];
    }
}
