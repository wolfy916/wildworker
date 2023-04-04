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

    private StationDto station;
    private String type;
    private Long value;
    private boolean applied;
    private String time;
}
