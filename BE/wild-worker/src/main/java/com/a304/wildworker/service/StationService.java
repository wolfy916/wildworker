package com.a304.wildworker.service;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.exception.StationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StationService {

    private final StationRepository stationRepository;

    public void changeCommission(long stationId, long value) {
        Station station = stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);
        station.changeCommission(value);
    }
}
