package com.a304.wildworker.service;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.dto.request.MatchSelectRequest;
import com.a304.wildworker.dto.request.MiniGameProgressRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiniGameService {

    private final MatchRepository matchRepository;

    private Match getMatchFromActiveUser(ActiveUser activeUser) {
        String matchId = activeUser.getCurrentMatchId();
        return matchRepository.findById(matchId);
    }

    public void select(ActiveUser activeUser, MatchSelectRequest request) {
        Duel isDuel = request.isDuel() ? Duel.DUEL : Duel.RUN;
        Match match = getMatchFromActiveUser(activeUser);
        match.addSelected(activeUser.getUserId(), isDuel);
        if (isDuel == Duel.RUN) {
            //TODO: 도망비 납부
        }
    }

    public void progress(ActiveUser activeUser, MiniGameProgressRequest request) {
        int result = request.getResult();
        Match match = getMatchFromActiveUser(activeUser);
        match.addProgress(activeUser.getUserId(), result);
    }

    public void startGame(Match match) {
        MiniGame game = MiniGameFactory.randomMiniGame();
        match.setMiniGame(game);
    }

}
