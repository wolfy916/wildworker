package com.a304.wildworker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SystemService {

    /* 두 지점 간의 거리(m) 계산 */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist =
            Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(
                deg2rad(lat2)) * Math.cos(deg2rad(theta));

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
