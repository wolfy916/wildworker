package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.station.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class StationDto {

    Long id;
    String name;

    public static StationDto of(Station station) {
        return new StationDto(station.getId(), station.getName());
    }
}
