package com.a304.wildworker.service;


import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.common.EventPublish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActiveStationService {

    private final ActiveStationRepository activeStationRepository;


    @EventPublish
    public void insertToStationPool(ActiveUser activeUser, long stationId) {
        ActiveStation activeStation = activeStationRepository.findById(stationId);
        activeUser.setActiveStation(activeStation);
        activeStation.insertToPool(activeUser.getUserId());
    }

    public void removeFromPool(long stationId, long userId) {
        ActiveStation activeStation = activeStationRepository.findById(stationId);
        activeStation.removeFromPool(userId);
    }

}
