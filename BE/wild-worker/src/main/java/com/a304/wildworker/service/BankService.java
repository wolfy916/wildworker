package com.a304.wildworker.service;


import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.ethereum.contract.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BankService {

    private final Bank bank;

    public void sendTransaction(TransactionType type, Station station, User user, long amount)
            throws Exception {
        switch (type) {
            case MINI_GAME_COST:
                bank.sendGameCost(station, user, amount);
                break;
            case MINI_GAME_RUN_COST:
                bank.sendRunCost(station, user, amount);
                break;
            case MINI_GAME_REWARD:
                bank.sendGameReward(station, user, amount);
                break;
        }
    }
}
