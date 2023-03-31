package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.dominator.DominatorLog;
import com.a304.wildworker.domain.dominator.DominatorLogRepository;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.event.ChangedBalanceEvent;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;

@RequiredArgsConstructor
@Slf4j
@Service
public class InvestService {

    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final DominatorLogRepository dominatorLogRepository;
    private final Bank bank;
    private final SystemData systemData;

    private final ApplicationEventPublisher publisher;

    /* 역 투자 */
    @Transactional
    public void investToStation(Long stationId, Long userId, Long amount)
            throws CipherException, IOException {
        User user = getUserOrElseThrow(userId);
        Station station = getStationOrElseThrow(stationId);

        user.invest(amount);
        station.invest(user, amount);

        // 코인 변동 이벤트 발생
        publisher.publishEvent(
                new ChangedBalanceEvent(user, station, TransactionType.INVESTMENT, amount * -1));
    }

    /**
     * 10분에 한 번 실행되는 메소드. 모든 역에 대해 투자 지분에 맞게 수수료를 분배한다.
     *
     * @throws CipherException
     * @throws IOException
     */
    @Scheduled(cron = "0 0/10 * * * *")
    public void distributeInvestReward() throws IOException {
        // 10분 타임라인 시간 갱신
        systemData.updateBaseTime();

        for (Station station : stationRepository.findAll()) {
            // 수수료 정산(DB) 후 새 지배자 얻기
            User dominator = distributeStationCommission(station);

            // 지배자 설정
            setNewDominator(station, dominator);

            // 이더리움에서도 정산 요청
            bank.distributeInvestReward(station).thenAccept(
                    (receipt -> log.info("distribute invest reward receipt : {}", receipt)));
        }
    }

    /**
     * 이 메소드는 실제 블록체인 네트워크에 기록된 내용이 아니므로 정확하지 않을 수 있음(소수점 처리 과정이 정확하지 않을 수 있음)
     */
    /* station의 수수료 정산 후 새 지배자를 반환 */
    @Transactional
    public User distributeStationCommission(Station station) {
        Map<User, Long> investors = station.getInvestors();
        User dominator = null;
        Long maxInvestment = 0L;

        for (Entry<User, Long> entry : investors.entrySet()) {
            User user = entry.getKey();
            Long investment = entry.getValue();

            // 지분율 1위 찾기
            if (maxInvestment <= investment) {
                maxInvestment = investment;
                dominator = user;
            }

            // 지분율에 따라 수수료 정산
            long userShare = (investment / station.getBalance()) * 1000;
            long money = (userShare * station.getCommission()) / 1000;
            user.changeBalance(money);

            // 코인 변동 이벤트 발생
            publisher.publishEvent(
                    new ChangedBalanceEvent(user, station, TransactionType.INVESTMENT_REWARD,
                            money));
        }

        // 누적 수수료 초기화
        station.resetCommission();

        //매 주 월요일 0시에는 누적금도 전부 초기화
        LocalDateTime nowDateTime = systemData.getNowBaseTime();
        DayOfWeek dayOfWeek = nowDateTime.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.MONDAY &&
                (nowDateTime.getHour() == 0 && nowDateTime.getMinute() == 0)) {
            station.resetBalance();
        }

        return dominator;
    }

    /* 지배자 설정 */
    @Transactional
    public void setNewDominator(Station station, User dominator) {
        // 현재 지배자가 존재하는 경우 설정
        if (dominator != null) {
            dominatorLogRepository.save(
                    new DominatorLog(station, dominator, systemData.getNowBaseTimeString()));
        }

        // 기존 지배자에서 변경된 경우 역 구독자들에게 Noti
        Optional<DominatorLog> prevDominator = dominatorLogRepository.findByStationIdAndDominateStartTime(
                station.getId(), systemData.getPrevBaseTimeString());

        boolean isChange = false;
        if (prevDominator.isPresent()) {
            if (dominator == null ||
                    prevDominator.get().getUser().getId() != dominator.getId()) {
                isChange = true;
            }
        } else {
            if (dominator != null) {
                isChange = true;
            }
        }

        if (isChange) {
            // 역 구독자에게 변동사항 브로드캐스트
            
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
