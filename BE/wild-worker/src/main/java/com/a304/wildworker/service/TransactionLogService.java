package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.transaction.TransactionLog;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import com.a304.wildworker.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
