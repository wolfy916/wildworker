package com.a304.wildworker.domain.common;

import com.a304.wildworker.dto.response.common.SubType;

public enum TransactionType implements SubType {
    AUTO_MINING,
    MANUAL_MINING,
    MINI_GAME_COST,
    MINI_GAME_RUN_COST,
    MINI_GAME_REWARD,
    INVESTMENT,
    INVESTMENT_REWARD
}
