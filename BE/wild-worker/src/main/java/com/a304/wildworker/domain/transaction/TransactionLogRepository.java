package com.a304.wildworker.domain.transaction;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    public boolean existsByUserAndStationAndTypeAndCreatedAtGreaterThanEqual(User user,
            Station station, TransactionType type, LocalDateTime baseTime);
}
