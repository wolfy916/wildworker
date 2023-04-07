package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.event.ChangedBalanceEvent;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiningService {

    private final SystemData systemData;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final ActiveUserRepository activeUserRepository;

    private final ApplicationEventPublisher publisher;


    /* 수동 채굴 - 종이 줍기 */
    @Transactional
    public int manualMiningCollect(Long userId) {
        User user = getUserOrElseThrow(userId);

        // 종이 줍기 (누적 종이 개수 갱신)
        return user.collectPaper();
    }

    /* 수동 채굴 - 종이 팔기 */
    @Transactional
    public void manualMiningSell(Long userId) throws CipherException, IOException {
        User user = getUserOrElseThrow(userId);
        Station station = getStationOrElseThrow(Constants.ROOT_STATION_ID);

        // 종이 팔기
        user.sellPaper();

        // 코인 변동 이벤트 발생
        publisher.publishEvent(new ChangedBalanceEvent(user, station, TransactionType.MANUAL_MINING,
                Constants.AMOUNT_MANUAL_MINE));
    }

    /* 자동 채굴 */
    @Transactional
    public void autoMining(ActiveUser activeUser) {
        User user = getUserOrElseThrow(activeUser.getUserId());
        Station station = getStationOrElseThrow(activeUser.getStationId());

        boolean alreadyMining = transactionLogRepository.existsByUserAndStationAndTypeAndCreatedAtGreaterThanEqual(
                user, station, TransactionType.AUTO_MINING, systemData.getAutoMiningBaseTime());

        // 해당 역에서 자동 채굴을 하지 않았던 경우
        if (!alreadyMining) {
            // 코인 지급
            user.changeBalance(Constants.AMOUNT_AUTO_MINE);

            // 코인 변동 이벤트 발생
            publisher.publishEvent(
                    new ChangedBalanceEvent(user, station, TransactionType.AUTO_MINING,
                            Constants.AMOUNT_AUTO_MINE));
        }
    }

    /* 3시, 15시마다 자동 채굴 상태 초기화 */
    @Scheduled(cron = "0 0 3,15 * * *")
    public void initAutoMiningStatus() {
        // 시스템의 자동 채굴 상태 초기화 시간 갱신
        systemData.initAutoMiningTime();

        // 접속 유저 중에서 역 범위에 있는 유저는 자동 채굴 처리
        for (ActiveUser activeUser : activeUserRepository.findAll()) {
            if (activeUser.getStationId() > 0) {
                autoMining(activeUser);
            }
        }
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
