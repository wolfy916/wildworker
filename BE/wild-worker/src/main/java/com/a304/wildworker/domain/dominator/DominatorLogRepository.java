package com.a304.wildworker.domain.dominator;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DominatorLogRepository extends JpaRepository<DominatorLog, Long> {

    Optional<DominatorLog> findByStationIdAndDominateStartTime(long stationId,
            String dominateStartTime);
}
