package com.a304.wildworker.domain.minigame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniGameLogRepository extends JpaRepository<MiniGameLog, Long> {

    Page<MiniGameLog> findByUser1IdOrUser2IdOrderByCreatedAtDesc(Long userId1, Long userId2,
            Pageable pageable);
}
