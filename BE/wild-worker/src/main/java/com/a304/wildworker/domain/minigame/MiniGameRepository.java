package com.a304.wildworker.domain.minigame;

import com.a304.wildworker.domain.common.MiniGameCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniGameRepository extends JpaRepository<MiniGame, Long> {

    Optional<MiniGame> findByCode(MiniGameCode code);
}
