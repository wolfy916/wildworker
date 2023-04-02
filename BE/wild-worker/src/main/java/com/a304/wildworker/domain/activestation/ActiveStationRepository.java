package com.a304.wildworker.domain.activestation;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.transaction.TransactionLog;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveStationRepository {

    private ConcurrentHashMap<Long, ActiveStation> activeStations;

    private final SystemData systemData;
    private final TransactionLogRepository transactionLogRepository;

    public ActiveStationRepository(SystemData systemData,
            TransactionLogRepository transactionLogRepository) {
        activeStations = new ConcurrentHashMap<>();
        this.systemData = systemData;
        this.transactionLogRepository = transactionLogRepository;

        for (Long id = 1L; id <= Constants.STATION_COUNT; id++) {
            save(new ActiveStation(id));
        }

        // 투자내역 세팅
        List<TransactionLog> transactionLogList = transactionLogRepository.findByTypeAndCreatedAtGreaterThanEqual(
                TransactionType.INVESTMENT, systemData.getInvestmentBaseTime());

        for (TransactionLog investmentLog : transactionLogList) {
            ActiveStation station = findById(investmentLog.getStation().getId());
            station.invest(investmentLog.getUser(), investmentLog.getValue() * -1);
        }

    }

    public ActiveStation findById(Long id) {
        return Optional.ofNullable(activeStations.get(id))
                .orElseGet(() -> save(new ActiveStation(id)));
    }

    private ActiveStation save(ActiveStation station) {
        activeStations.put(station.getId(), station);
        return station;
    }

}
