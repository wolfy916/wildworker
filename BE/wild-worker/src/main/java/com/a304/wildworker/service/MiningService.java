package com.a304.wildworker.service;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.exception.PaperTooLowException;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiningService {

    private final Bank bank;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    private static final int SELL_LIMIT = 100; // 종이 판매 단위

    @Transactional
    public void sellPaper(Long userId) throws CipherException, IOException {
        User user = getOrElseThrow(userId);

        if (user.getNumberOfCollectedPaper() < SELL_LIMIT) {
            throw new PaperTooLowException();
        }

        user.sellPaper();
        bank.manualMine(user);
    }

    public void giveWonFromStationToUser(Long stationId, Long userId)
            throws CipherException, IOException {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);

        User user = getOrElseThrow(userId);

        bank.autoMine(station, user);
    }

    private User getOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
