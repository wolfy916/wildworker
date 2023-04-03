package com.a304.wildworker.dto.response;


import com.a304.wildworker.domain.common.MiniGameCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MiniGameStartResponse {

    private int gameType;
    private int timeLimit;

    public static MiniGameStartResponse of(MiniGameCode code) {
        return new MiniGameStartResponse(code.ordinal(), code.getTimeLimit());
    }
}
