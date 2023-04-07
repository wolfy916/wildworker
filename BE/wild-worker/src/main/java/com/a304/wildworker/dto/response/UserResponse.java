package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class UserResponse {

    private String name;
    private int characterType;
    private TitleDto title;
    private int titleType;
    private long coin;
    private int collectedPapers;

    public static UserResponse of(User user, TitleDto title) {
        return UserResponse.builder()
                .name(user.getName())
                .characterType(user.getCharacterId().ordinal())
                .titleType(user.getTitleShowType().ordinal())
                .title(title)
                .coin(user.getBalance())
                .collectedPapers(user.getNumberOfCollectedPaper())
                .build();
    }
}
