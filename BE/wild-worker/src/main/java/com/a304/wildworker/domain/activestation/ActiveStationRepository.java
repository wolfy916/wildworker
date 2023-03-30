package com.a304.wildworker.domain.activestation;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveStationRepository {

    private ConcurrentHashMap<Long, ActiveStation> activeStations;

    public ActiveStationRepository() {
        activeStations = new ConcurrentHashMap<>();
    }

    public ActiveStation findById(Long id) {
        return Optional.ofNullable(activeStations.get(id))
                .orElseGet(() -> save(new ActiveStation(id)));
    }

    private ActiveStation save(ActiveStation station) {
        activeStations.put(station.getId(), station);
        return station;
    }

}
