package com.a304.wildworker.domain.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PersonalWinCode {
    DRAW,
    WIN,
    LOSE;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
