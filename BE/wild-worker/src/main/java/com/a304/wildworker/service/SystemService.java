package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.dominator.DominatorLogRepository;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
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
    private final DominatorLogRepository dominatorLogRepository;
    private final SystemData systemData;

    private final MiningService miningService;

    /* 두 지점 간의 거리(m) 계산 */
    private static double getDistance(Location location1, Location location2) {
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
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /* radians to decimal degrees */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /* 유저의 현재 좌표를 기준으로 역 조회 후 진입이나 이탈 여부 판단 */
    public StationWithUserResponse checkUserLocation(ActiveUser user, Location userLocation) {
        StationWithUserResponse stationWithUserResponse = null;

        // 유저가 위치하고 있는 역 조회
        StationInfoResponse stationInfoResponse
                = convertToStationInfoResponse(getStationFromLatLon(userLocation));
        long currentStationId = (stationInfoResponse != null) ? stationInfoResponse.getId() : 0;

        // 기존 상태와 달라진 경우
        if (currentStationId != user.getStationId()) {
            // 접속 유저의 현재 역 정보 갱신
            user.setStationId(currentStationId);

            // 특정 역 범위에 들어갈 경우
            if (currentStationId != 0) {
                // 자동 채굴 체크
                miningService.autoMining(user);
            }

            // TODO: 일단 current만 새로 채워 보냄.. 방향 판단하여 prev, next 채우는 것은 추후 예정
            stationWithUserResponse
                    = new StationWithUserResponse(null, stationInfoResponse, null);
        }

        return stationWithUserResponse;
    }

    /* Station ->  StationInfoResponse 로 변환 */
    public StationInfoResponse convertToStationInfoResponse(Station station) {
        if (station == null) {
            return null;
        }

        // 해당 역의 지배자 정보 조회
//        Optional<DominatorLog> dominator = dominatorLogRepository.findByStationIdAndDominateStartTime(
//                station.getId(), systemData.getNowBaseTimeString());

        // 해당 역에 지배자가 있는 경우 이름 가져오기
        String dominatorName = null;
//        if (dominator.isPresent()) {
//            dominatorName = dominator.get().getUser().getName();
//        }

        return StationInfoResponse.of(station, dominatorName);
    }

    /* 입력받은 위경도가 범위에 포함되는 역 반환, 없으면 null */
    private Station getStationFromLatLon(Location userLocation) {
        Station findStation = null;
        List<Station> stationList = stationRepository.findAll();

        // 역과의 거리 계산
        for (Station station : stationList) {
            double dist = getDistance(userLocation, station.getLocation());

            // 유저가 현재 역의 범위에 들어갈 경우
            if (dist <= Constants.STATION_RANGE) {
                findStation = station;
                break;
            }
        }

        return findStation;
    }

}
