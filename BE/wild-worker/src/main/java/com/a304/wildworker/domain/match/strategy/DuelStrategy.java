package com.a304.wildworker.domain.match.strategy;

import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.RunCode;

public interface DuelStrategy {

    Duel decideDuel(RunCode runCode);
}
