package com.a304.wildworker.domain.minigame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniGameLogRepository extends JpaRepository<MiniGameLog, Long> {

}
