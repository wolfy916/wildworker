package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.TransactionEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.event.common.Events;
import com.a304.wildworker.exception.StationNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchTransactionService {

    private final StationRepository stationRepository;

    @EventPublish
    public void transactionRunCost(Match match) {
        Station station = findStationById(match.getStationId());
        long cost = match.getRunCost();
        for (User user : match.getUsers()) {
            if (match.isRunner(user.getId())) {
                Events.raise(TransactionEvent.of(
                        TransactionType.MINI_GAME_RUN_COST,
                        station, user, cost));
            }
        }
    }

    @EventPublish
    public void transactionGameCost(Match match) {
        Station station = findStationById(match.getStationId());
        long cost = match.getCost();
        for (User user : match.getUsers()) {
            Events.raise(
                    TransactionEvent.of(TransactionType.MINI_GAME_COST, station, user, cost));
        }
    }

    @EventPublish
    public void transactionReward(Match match) {
        Station station = findStationById(match.getStationId());
        for (User user : match.getUsers()) {
            long amount = match.getReward(user.getId()) + match.getCommission(user.getId());
            Events.raise(
                    TransactionEvent.of(TransactionType.MINI_GAME_REWARD, station, user, amount));
        }
    }

    private Station findStationById(long id) {
        return stationRepository.findById(id).orElseThrow(StationNotFoundException::new);
    }
}
