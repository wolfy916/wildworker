package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.station.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class StationInfoResponse {

    private long id;
    private String name;
    private String dominator;

    public static StationInfoResponse of(Station station, String dominator) {
        return StationInfoResponse.builder()
                .id(station.getId())
                .name(station.getName())
                .dominator(dominator)
                .build();
    }
}
