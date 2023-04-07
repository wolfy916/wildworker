package com.a304.wildworker.domain.common;

import java.util.List;
import java.util.Random;

public enum Duel {
    RUN,
    DUEL;

    private static final List<Duel> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Duel random() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }


}
