package com.a304.wildworker.domain.activestation;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class StationPoolRepository {

    private ConcurrentHashMap<Long, StationPool> activeStations;

    public StationPoolRepository() {
        activeStations = new ConcurrentHashMap<>();
    }

    public StationPool findById(Long id) {
        return Optional.ofNullable(activeStations.get(id))
                .orElseGet(() -> save(new StationPool(id)));
    }

    private StationPool save(StationPool station) {
        activeStations.put(station.getId(), station);
        return station;
    }

}
