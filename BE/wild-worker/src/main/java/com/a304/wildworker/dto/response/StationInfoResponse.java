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

    public static StationInfoResponse of(Station station) {
        return StationInfoResponse.builder()
            .id(station.getId())
            .name(station.getName())
//            .dominator()  //TODO. 투자 지분 테이블 생성 후 가져와야 함
            .build();
    }
}
