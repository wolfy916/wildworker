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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;

@EnableScheduling
@RequiredArgsConstructor
@Slf4j
@Service
public class InvestService {

    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final Bank bank;

    public void investToStation(Long stationId, Long userId, Long amount)
            throws CipherException, IOException {
        Station station = getStationOrThrow(stationId);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        bank.invest(station, user, amount)
                .thenAccept(receipt -> log.info("invest receipt : {}", receipt));

        user.invest(amount);
        station.invest(user, amount);
    }

    /**
     * 10분에 한 번 실행되는 메소드. 모든 역에 대해 투자 지분에 맞게 수수료를 분배한다.
     *
     * @throws CipherException
     * @throws IOException
     */
    @Scheduled(cron = "0 0/10 * * * *")
    public void distributeInvestReward() throws CipherException, IOException {
        for (Station station : stationRepository.findAll()) {

            bank.distributeInvestReward(station)
                    .thenAccept((receipt -> log.info("distribute invest reward receipt : {}",
                            receipt)));
            station.distributeCommission();
            station.resetCommission();

            // TODO: 2023-03-28 지배자 변동
        }
    }

    private Station getStationOrThrow(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);
    }
}
