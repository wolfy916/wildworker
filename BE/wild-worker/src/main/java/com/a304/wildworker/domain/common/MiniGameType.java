package com.a304.wildworker.domain.common;

import java.util.List;
import java.util.Random;

public enum MiniGameType {
    CALCULATE_GAME,         //계산 게임
    CLICKER_GAME,           //두더지잡기
    ROCK_PAPER_SCISSORS;    //가위바위보

    private static final List<MiniGameType> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static MiniGameType random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
