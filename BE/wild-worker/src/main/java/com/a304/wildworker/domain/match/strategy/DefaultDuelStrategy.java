package com.a304.wildworker.domain.match.strategy;

import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.RunCode;

public class DefaultDuelStrategy implements DuelStrategy {

    @Override
    public Duel decideDuel(RunCode runCode) {
        if (runCode == RunCode.NONE) {
            return Duel.DUEL;
        } else if (runCode == RunCode.ALL) {
            return Duel.RUN;
        } else {
            return Duel.random();
        }
    }
}
