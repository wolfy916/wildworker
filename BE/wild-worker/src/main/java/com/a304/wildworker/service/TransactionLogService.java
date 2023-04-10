package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.transaction.TransactionLog;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.TransactionLogAppliedEvent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;

    /**
     * 로그 생성
     *
     * @param type
     * @param station
     * @param user
     * @param value   user 기준 +- coin 값
     */
    public void create(TransactionType type, Station station, User user, long value) {
        TransactionLog log = new TransactionLog(user, station, value, type);
        transactionLogRepository.save(log);
    }

    @Transactional
    @EventListener
    public void setAppliedAt(TransactionLogAppliedEvent event) {
        TransactionLog transactionLog = transactionLogRepository.findById(event.getTransactionId())
                .get();
        LocalDateTime now = LocalDateTime.now();
        transactionLog.setAppliedAt(now);
        log.info("transaction applied at : {}", now);
    }
}
