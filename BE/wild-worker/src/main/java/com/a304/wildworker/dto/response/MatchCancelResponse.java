package com.a304.wildworker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(staticName = "of")
public class MatchCancelResponse {

    private boolean isRunner;
}