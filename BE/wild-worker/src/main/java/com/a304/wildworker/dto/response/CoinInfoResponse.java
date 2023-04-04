package com.a304.wildworker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class CoinInfoResponse {

    StationDto station;
    String type;
    Long value;
    boolean applied;
    String time;
}
