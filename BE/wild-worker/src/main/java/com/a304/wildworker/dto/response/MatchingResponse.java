package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MatchingResponse {

    private String id;
    private long cost;
    private long runCost;
    private UserDto enemy;
    private int timeLimit;

    public static MatchingResponse of(Match match, UserDto enemy) {
        return MatchingResponse.builder()
                .id(match.getId())
                .cost(match.getCost())
                .runCost(match.getRunCost())
                .enemy(enemy)
                .timeLimit(match.getTimeLimitSec())
                .build();
    }

    @Getter
    @ToString
    @Builder
    public static class UserDto {

        private String name;
        private String title;
        private int characterType;

        public static UserDto of(User user, TitleDto titleDto) {
            return UserDto.builder()
                    .name(user.getName())
                    .title(titleDto.getName())
                    .characterType(user.getCharacterId().ordinal())
                    .build();
        }
    }

}
