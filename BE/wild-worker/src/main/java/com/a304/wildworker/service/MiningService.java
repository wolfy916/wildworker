package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.event.ChangedBalanceEvent;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher publisher;


    /* 수동 채굴 - 종이 줍기 */
    @Transactional
    public int collectPaper(Long userId) {
        User user = getUserOrElseThrow(userId);

        // 종이 줍기 (누적 종이 개수 갱신)
        return user.collectPaper();
    }

    /* 수동 채굴 - 종이 팔기 */
    @Transactional
    public void sellPaper(Long userId) throws CipherException, IOException {
        User user = getUserOrElseThrow(userId);
        Station station = getStationOrElseThrow(Constants.ROOT_STATION_ID);

        // 종이 팔기
        user.sellPaper();

        // 코인 변동 이벤트 발생
        publisher.publishEvent(new ChangedBalanceEvent(user, station, TransactionType.MANUAL_MINING,
                Constants.AMOUNT_MANUAL_MINE));
    }

    public void giveWonFromStationToUser(Long stationId, Long userId)
            throws CipherException, IOException {
        Station station = getStationOrElseThrow(stationId);
        User user = getUserOrElseThrow(userId);

        bank.autoMine(station, user);
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Station getStationOrElseThrow(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);
    }
}
