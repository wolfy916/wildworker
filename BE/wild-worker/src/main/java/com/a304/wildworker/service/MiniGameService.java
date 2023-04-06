package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.minigame.MiniGameRepository;
import com.a304.wildworker.exception.NoSuchCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MiniGameService {

    private final MiniGameRepository miniGameRepository;

    public MiniGame findByCode(MiniGameCode code) {
        return miniGameRepository.findByCode(code)
                .orElseThrow(() -> new NoSuchCodeException(code));
    }

    public MiniGame createMiniGame() {
        MiniGameCode code = MiniGameCode.random();
        return findByCode(code);
    }
}
