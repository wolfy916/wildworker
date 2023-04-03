package com.a304.wildworker.service;


import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActiveStationService {

    private final ActiveStationRepository activeStationRepository;

    public void removeFromPool(long stationId, long userId) {
        ActiveStation activeStation = activeStationRepository.findById(stationId);
        activeStation.removeFromPool(userId);
    }

}
