package com.a304.wildworker.service;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvestService {

    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final Bank bank;

    public void investToStation(Long stationId, Long userId, Long amount)
            throws CipherException, IOException {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        bank.invest(station, user, amount)
                .thenAccept(receipt -> log.info("invest receipt : {}", receipt));

        user.invest(amount);
        station.invest(user, amount);
    }

}
