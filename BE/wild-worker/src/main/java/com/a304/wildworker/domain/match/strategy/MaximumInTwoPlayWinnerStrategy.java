package com.a304.wildworker.domain.match.strategy;

import com.a304.wildworker.exception.MatchWrongNumOfPlayerException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class MaximumInTwoPlayWinnerStrategy implements WinnerStrategy {

    private static final int MAX_NUMBER_OF_PLAYER = 2;

    public Long getWinner(Map<Long, Integer> progress) {
        if (progress.size() != MAX_NUMBER_OF_PLAYER) {
            throw new MatchWrongNumOfPlayerException(MAX_NUMBER_OF_PLAYER, progress.size());
        }
        var sorted = progress.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());
        if (sorted.get(0).getValue().equals(sorted.get(1).getValue())) {
            return null;
        } else {
            return sorted.get(0).getKey();
        }
    }
}
