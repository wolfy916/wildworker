package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.minigame.MiniGameRepository;
import com.a304.wildworker.exception.NoSuchCodeException;
import org.springframework.beans.factory.annotation.Autowired;

public class MiniGameFactory {

    @Autowired
    private static MiniGameRepository miniGameRepository;

    public static MiniGame findByCode(MiniGameCode code) {
        switch (code) {
            case CALCULATE_GAME:
            case CLICKER_GAME:
            case ROCK_PAPER_SCISSORS:
                return miniGameRepository.findByCode(code)
                        .orElseThrow(() -> new NoSuchCodeException(code));
            default:
                throw new IllegalArgumentException("Unknown code: " + code);
        }
    }
}
