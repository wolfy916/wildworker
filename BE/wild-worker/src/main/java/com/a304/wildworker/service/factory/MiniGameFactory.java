package com.a304.wildworker.service.factory;

import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.minigame.MiniGameRepository;
import com.a304.wildworker.exception.NoSuchCodeException;
import org.springframework.beans.factory.annotation.Autowired;

public class MiniGameFactory {

    @Autowired
    private static MiniGameRepository miniGameRepository;

    public static MiniGame findByCode(MiniGameCode code) {
        return miniGameRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchCodeException(code));
    }

    public static MiniGame randomMiniGame() {
        MiniGameCode code = MiniGameCode.random();
        return findByCode(code);
    }
}
