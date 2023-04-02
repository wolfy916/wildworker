package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.dominator.DominatorLog;
import com.a304.wildworker.domain.dominator.DominatorLogRepository;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.InvestmentInfoResponse;
import com.a304.wildworker.dto.response.InvestmentRankResponse;
import com.a304.wildworker.dto.response.MyInvestmentInfoResponse;
import com.a304.wildworker.dto.response.MyInvestmentResponse;
import com.a304.wildworker.dto.response.StationDto;
import com.a304.wildworker.dto.response.common.StationType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.event.ChangedBalanceEvent;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final ActiveStationRepository activeStationRepository;
    private final Bank bank;
    private final SystemData systemData;

    private final ApplicationEventPublisher publisher;
    private final SimpMessagingTemplate messagingTemplate;

    /* 해당 역에 대한 지분 조회 */
    public InvestmentInfoResponse showInvestmentByStation(Long stationId, Long userId)
            throws IOException {
        User user = getUserOrElseThrow(userId);
        Station station = getStationOrElseThrow(stationId);
        ActiveStation activeStation = activeStationRepository.findById(station.getId());
        Map<Long, Long> investors = activeStation.getInvestors();

        // 해당 역의 지배자 정보 조회
        Optional<DominatorLog> dominator = dominatorLogRepository.findByStationIdAndDominateStartTime(
                stationId, systemData.getNowBaseTimeString());

        // 해당 역에 지배자가 있는 경우 이름 가져오기
        String dominatorName = null;
        if (dominator.isPresent()) {
            dominatorName = dominator.get().getUser().getName();
        }

        // 랭킹 정보
        List<InvestmentRankResponse> rankList = new ArrayList<>(5);
        InvestmentRankResponse mine = null;
        List<Entry<Long, Long>> entryList = new ArrayList<>(investors.entrySet());
        entryList.sort(Map.Entry.<Long, Long>comparingByValue().reversed());

        int rank = 1;
        for (Map.Entry<Long, Long> entry : entryList) {
            InvestmentRankResponse rankResponse = null;

            // 5위까지 세팅
            if (rank <= 5) {
                rankResponse = getInvestmentRankResponse(rank, entry, station);
                rankList.add(rankResponse);
            }

            // 내 지분 정보
            if (entry.getKey().equals(userId)) {
                if (rankResponse == null) {
                    rankResponse = getInvestmentRankResponse(rank, entry, station);
                }

                mine = rankResponse;
            }

            rank++;
        }

        InvestmentInfoResponse response = InvestmentInfoResponse.builder()
                .stationName(station.getName())
                .dominator(dominatorName)
                .totalInvestment(station.getBalance())
                .prevCommission(station.getPrevCommission())
                .currentCommission(station.getCommission())
                .ranking(rankList)
                .mine(mine)
                .build();

        return response;
    }

    public InvestmentRankResponse getInvestmentRankResponse(int rank, Map.Entry<Long, Long> entry,
            Station station) {
        User user = getUserOrElseThrow(entry.getKey());
        return InvestmentRankResponse.builder()
                .rank(rank)
                .name(user.getName())
                .investment(entry.getValue())
                .percent((int) ((double) entry.getValue() / station.getBalance()) * 100)
                .build();
    }

    /* 내가 투자한 역 목록 조회 */
    public MyInvestmentResponse showMyInvestment(Long userId, String orderBy, String ascend)
            throws IOException {
        User user = getUserOrElseThrow(userId);
        List<MyInvestmentInfoResponse> investmentList = new LinkedList<>();

        // 내 투자 리스트
        for (Long id = 1L; id <= Constants.STATION_COUNT; id++) {
            ActiveStation activeStation = activeStationRepository.findById(id);
            Map<Long, Long> investors = activeStation.getInvestors();

            Long amount = investors.get(user.getId());
            if (amount != null) {
                Station station = getStationOrElseThrow(id);
                investmentList.add(MyInvestmentInfoResponse.builder()
                        .station(new StationDto(station.getId(), station.getName()))
                        .investment(amount)
                        .percent(getPercent(station.getBalance(), amount))
                        .build());
            }
        }

        // 이름순 정렬
        if (orderBy.equals("name")) {
            if (ascend.equals("ASC")) {
                Collections.sort(investmentList,
                        Comparator.comparing(i -> i.getStation().getName()));
            } else {
                Collections.sort(investmentList, Comparator.comparing(i -> i.getStation().getName(),
                        Comparator.reverseOrder()));
            }
        }
        // 투자금액순 정렬
        else if (orderBy.equals("investment")) {
            if (ascend.equals("ASC")) {
                Collections.sort(investmentList,
                        Comparator.comparing(MyInvestmentInfoResponse::getInvestment));
            } else {
                Collections.sort(investmentList,
                        Comparator.comparing(MyInvestmentInfoResponse::getInvestment,
                                Comparator.reverseOrder()));
            }
        }
        // 지분율순 정렬
        else if (orderBy.equals("percent")) {
            if (ascend.equals("ASC")) {
                Collections.sort(investmentList,
                        Comparator.comparing(MyInvestmentInfoResponse::getPercent));
            } else {
                Collections.sort(investmentList,
                        Comparator.comparing(MyInvestmentInfoResponse::getPercent,
                                Comparator.reverseOrder()));
            }
        }

        // 수수료 정산까지 남은 시간
        int remainSec = (int) Duration.between(LocalDateTime.now(), systemData.getNextBaseTime())
                .getSeconds();

        MyInvestmentResponse response = MyInvestmentResponse.builder()
                .investments(investmentList)
                .remainSec(remainSec)
                .orderBy(orderBy)
                .ascend(ascend)
                .build();

        return response;
    }

    public int getPercent(Long allValue, Long myValue) {
        return (int) (((double) myValue / allValue) * 100);
    }

    /* 역 투자 */
    @Transactional
    public void investToStation(Long stationId, Long userId, Long amount)
            throws CipherException, IOException {
        User user = getUserOrElseThrow(userId);
        Station station = getStationOrElseThrow(stationId);
        ActiveStation activeStation = activeStationRepository.findById(stationId);

        user.invest(amount);
        station.invest(amount);
        activeStation.invest(user.getId(), amount);

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
    @Transactional
    public void distributeInvestReward() throws IOException {
        // 10분 타임라인 시간 갱신
        systemData.updateBaseTime();

        // 투자금 초기화 시간 갱신
        LocalDateTime nowDateTime = systemData.getNowBaseTime();
        DayOfWeek dayOfWeek = nowDateTime.getDayOfWeek();
        boolean isInitInvestmentTime = false;

        if (dayOfWeek == DayOfWeek.MONDAY &&
                (nowDateTime.getHour() == 0 && nowDateTime.getMinute() == 0)) {
            systemData.initInvestmentTime();
            isInitInvestmentTime = true;
        }

        for (Station station : stationRepository.findAll()) {
            // 수수료 정산(DB) 후 새 지배자 얻기
            User dominator = distributeCommissionAndGetDominator(station);

            // 누적 수수료 초기화
            station.resetCommission();

            //매 주 월요일 0시에는 누적금도 전부 초기화
            if (isInitInvestmentTime) {
                station.resetBalance();
            }

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
    public User distributeCommissionAndGetDominator(Station station) {
        ActiveStation activeStation = activeStationRepository.findById(station.getId());
        Map<Long, Long> investors = activeStation.getInvestors();
        User dominator = null;
        Long maxInvestment = 0L;

        for (Entry<Long, Long> entry : investors.entrySet()) {
            User user = getUserOrElseThrow(entry.getKey());
            Long investment = entry.getValue();

            // 지분율 1위 찾기
            if (maxInvestment <= investment) {
                maxInvestment = investment;
                dominator = user;
            }

            // 지분율에 따라 수수료 정산
            long userShare = (long) ((double) investment / station.getBalance()) * 1000;
            long money = (userShare * station.getCommission()) / 1000;
            user.changeBalance(money);

            // 코인 변동 이벤트 발생
            publisher.publishEvent(
                    new ChangedBalanceEvent(user, station, TransactionType.INVESTMENT_REWARD,
                            money));
        }

        return dominator;
    }

    /* 지배자 설정 */
    @Transactional
    public void setNewDominator(Station station, User dominator) {
        String dominatorName = null;

        // 현재 지배자가 존재하는 경우 설정
        if (dominator != null) {
            dominatorName = dominator.getName();
            dominatorLogRepository.save(
                    new DominatorLog(station, dominator, systemData.getNowBaseTimeString()));
        }

        // 새로운 지배자를 역 구독자들에게 Noti (지배자가 변경되지 않은 경우에도 보냄)
        WSBaseResponse<String> response = WSBaseResponse.station(StationType.CHANGE_DOMINATOR)
                .data(dominatorName);

        messagingTemplate.convertAndSend("/sub/stations/" + station.getId(), response);
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
