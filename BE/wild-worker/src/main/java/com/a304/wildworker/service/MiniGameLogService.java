package com.a304.wildworker.service;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.minigame.MiniGameLog;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiniGameLogService {

    private final MiniGameLogRepository miniGameLogRepository;

    public void saveLog(Match match) {
        MiniGameLog log = MiniGameLog.builder()
                .game(match.getMiniGame())
                .user1(match.getUsers().get(0))
                .user2(match.getUsers().get(1))
                .runCode(match.getRunCode())
                .resultCode(match.getResultCode())
                .build();
        miniGameLogRepository.save(log);
    }
}
