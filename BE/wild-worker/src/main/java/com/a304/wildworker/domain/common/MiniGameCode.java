package com.a304.wildworker.domain.common;

import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MiniGameCode {
    CALCULATE_GAME(30),         //계산 게임
    CLICKER_GAME(30),           //두더지잡기
    ROCK_PAPER_SCISSORS(7);    //가위바위보

    private static final List<MiniGameCode> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    @Getter
    private final int timeLimit;

    public static MiniGameCode random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
