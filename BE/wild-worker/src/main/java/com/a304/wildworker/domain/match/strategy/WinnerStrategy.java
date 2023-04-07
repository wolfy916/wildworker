package com.a304.wildworker.domain.match.strategy;

import java.util.Map;

public interface WinnerStrategy {

    Long getWinner(Map<Long, Integer> progress);
}
