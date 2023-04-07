package com.a304.wildworker.domain.transaction;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    public boolean existsByUserAndStationAndTypeAndCreatedAtGreaterThanEqual(User user,
            Station station, TransactionType type, LocalDateTime baseTime);

    public List<TransactionLog> findByTypeAndCreatedAtGreaterThanEqual(TransactionType type,
            LocalDateTime baseTime);

    public Page<TransactionLog> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
