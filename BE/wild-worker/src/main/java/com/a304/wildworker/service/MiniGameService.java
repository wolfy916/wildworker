package com.a304.wildworker.service;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.dto.request.MatchSelectRequest;
import com.a304.wildworker.dto.request.MiniGameProgressRequest;
import com.a304.wildworker.event.TransactionEvent;
import com.a304.wildworker.event.common.Events;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.service.factory.MiniGameFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiniGameService {

    private final MatchRepository matchRepository;
    private final StationRepository stationRepository;

    private Match getMatchFromActiveUser(ActiveUser activeUser) {
        String matchId = activeUser.getCurrentMatchId();
        return matchRepository.findById(matchId);
    }

    public void insertMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(String matchId) {
        matchRepository.deleteById(matchId);
    }

    public void select(ActiveUser activeUser, MatchSelectRequest request) {
        Duel isDuel = request.isDuel() ? Duel.DUEL : Duel.RUN;
        Match match = getMatchFromActiveUser(activeUser);
        match.addSelected(activeUser.getUserId(), isDuel);
    }

    public void progress(ActiveUser activeUser, MiniGameProgressRequest request) {
        int result = request.getResult();
        Match match = getMatchFromActiveUser(activeUser);
        match.addPersonalProgress(activeUser.getUserId(), result);
    }

    public void startGame(Match match) {
        MiniGame game = MiniGameFactory.randomMiniGame();
        match.setMiniGame(game);
    }


    public void transactionRunCost(Match match) {
        Station station = findStationById(match.getStationId());
        long cost = match.getRunCost();
        for (User user : match.getUsers()) {
            if (match.isRunner(user.getId())) {
                Events.raise(TransactionEvent.of(TransactionType.MINI_GAME_RUN_COST, station, user,
                        cost));
            }
        }
    }

    public void transactionGameCost(Match match) {
        Station station = findStationById(match.getStationId());
        long cost = match.getCost();
        for (User user : match.getUsers()) {
            Events.raise(
                    TransactionEvent.of(TransactionType.MINI_GAME_COST, station, user, cost));
        }
    }

    public void transactionReward(Match match) {
//        Station station = findStationById(match.getStationId());
//        long reward = match.getReward(Constants.COMMISSION_RATE);
//        for (User user : match.getUsers()) {
//            Events.raise(
//                    TransactionEvent.of(TransactionType.MINI_GAME_REWARD, station, user, reward));
//        }
    }

    private Station findStationById(long id) {
        return stationRepository.findById(id).orElseThrow(StationNotFoundException::new);
    }
}
