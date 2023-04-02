package com.a304.wildworker.domain.activestation;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class StationPoolRepository {

    private final ConcurrentHashMap<Long, StationPool> stationPools;

    public StationPoolRepository() {
        stationPools = new ConcurrentHashMap<>();
    }

    public StationPool findById(Long id) {
        return Optional.ofNullable(stationPools.get(id))
                .orElseGet(() -> save(new StationPool(id)));
    }

    private StationPool save(StationPool station) {
        stationPools.put(station.getId(), station);
        return station;
    }

}
