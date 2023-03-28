package com.a304.wildworker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CoinChangeResponse {

    Long balance;
    Long value;
}
