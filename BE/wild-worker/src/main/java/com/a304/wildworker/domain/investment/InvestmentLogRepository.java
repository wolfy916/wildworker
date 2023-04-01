package com.a304.wildworker.domain.investment;

import com.a304.wildworker.domain.station.Station;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentLogRepository extends JpaRepository<InvestmentLog, Long> {

    public List<InvestmentLog> findByStationAndCreatedAtGreaterThanEqualAndCreatedAtBefore(
            Station station, LocalDateTime createdAtStart, LocalDateTime createdAtEnd);
}
