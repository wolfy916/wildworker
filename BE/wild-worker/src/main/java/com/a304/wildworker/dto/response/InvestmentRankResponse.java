package com.a304.wildworker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class InvestmentRankResponse {

    int rank;
    String name;
    Long investment;
    int percent;
}
