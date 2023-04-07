package com.a304.wildworker.domain.station;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    boolean existsById(@NotNull Long id);

    List<Station> findByIdGreaterThan(Long id);
}
