package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.dto.response.StationInfoResponse;
import com.a304.wildworker.dto.response.StationWithUserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SystemService {

    private final StationRepository stationRepository;

    /* 유저의 현재 좌표를 기준으로 역 조회 후 진입이나 이탈 여부 판단 */
    public StationWithUserResponse checkUserLocation(ActiveUser user, Location userLocation) {
        StationWithUserResponse stationWithUserResponse = null;
        StationInfoResponse stationInfoResponse = getStationFromLatLon(userLocation);

        // 특정 역의 범위에 들어간 경우
        if (stationInfoResponse != null) {

            // 기존 상태와 다른 경우
            if (user.getStationId() != stationInfoResponse.getId()) {
                log.info(stationInfoResponse.getName() + " 진입");
                user.setStationId(stationInfoResponse.getId());

                // TODO: 일단 current만 새로 채워 보냄.. 방향 판단하여 prev, next 채우는 것은 추후 예정
                stationWithUserResponse
                    = new StationWithUserResponse(null, stationInfoResponse, null);
            }

        }
        // 어느 역에도 들어가지 않은 경우
        else {

            // 기존 상태와 다른 경우
            if (user.getStationId() != 0) {
                log.info("역 이탈");
                user.setStationId(0);

                // TODO: 일단 다 비워서 보냄..
                stationWithUserResponse
                    = new StationWithUserResponse(null, null, null);
            }
        }

        return stationWithUserResponse;
    }

    /* 입력받은 위경도가 범위에 포함되는 역 info 반환, 없으면 null */
    public StationInfoResponse getStationFromLatLon(Location userLocation) {
        StationInfoResponse stationInfoResponse = null;
        List<Station> stationList = stationRepository.findAll();

        // 역과의 거리 계산
        for (Station station : stationList) {
            double dist = getDistance(userLocation, station.getLocation());

            // 유저가 현재 역의 범위에 들어갈 경우
            if (dist <= Constants.STATION_RANGE) {
                stationInfoResponse = StationInfoResponse.of(station);
                break;
            }
        }

        return stationInfoResponse;
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
