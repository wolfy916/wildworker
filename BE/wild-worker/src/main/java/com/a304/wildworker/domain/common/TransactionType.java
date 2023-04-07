package com.a304.wildworker.domain.common;

import com.a304.wildworker.dto.response.common.SubType;

public enum TransactionType implements SubType {
    AUTO_MINING("자동채굴"),
    MANUAL_MINING("수동채굴"),
    MINI_GAME_COST("게임비"),
    MINI_GAME_RUN_COST("도망비"),
    MINI_GAME_REWARD("게임상금"),
    INVESTMENT("투자"),
    INVESTMENT_REWARD("수수료");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
