package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SystemService {

    private final StationRepository stationRepository;

    /* 유저의 현재 좌표를 기준으로 역 조회 & 진입이나 이탈 여부 판단 */
    public void checkUserLocation(ActiveUser activeUser, Location userLocation) {

        long stationId = getStationFromLatLon(userLocation);

        // 특정 역의 범위에 들어간 경우
        if (stationId > 0) {

            // 기존 상태와 다른 경우
            if (activeUser.getStationId() != stationId) {
                // TODO: 역 진입 멘트
            }

        }
        // 어느 역에도 들어가지 않은 경우
        else {

            // 기존 상태와 다른 경우
            if (activeUser.getStationId() != stationId) {
                // TODO: 역 이탈 멘트
            }
        }
    }

    /* 입력받은 위경도가 범위에 포함되는 역 id 반환, 없으면 0 */
    public long getStationFromLatLon(Location userLocation) {
        long stationId = 0;
        List<Station> stationList = stationRepository.findAll();

        // 역과의 거리 계산
        for (Station station : stationList) {
            double dist = getDistance(userLocation, station.getLocation());

            if (dist <= Constants.STATION_RANGE) {
                //유저가 현재 역의 범위에 들어갈 경우
                stationId = station.getId();
                break;
            }
        }

        return stationId;
    }

    /* 두 지점 간의 거리(m) 계산 */
    public static double getDistance(Location location1, Location location2) {
        double theta = location1.getLon() - location2.getLon();
        double dist =
            Math.sin(deg2rad(location1.getLat())) * Math.sin(deg2rad(location2.getLat()))
                + Math.cos(deg2rad(location1.getLat())) * Math.cos(
                deg2rad(location2.getLat())) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344; //mile to meter

        return dist;
    }

    /* decimal degrees to radians */
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* radians to decimal degrees */
    public static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
