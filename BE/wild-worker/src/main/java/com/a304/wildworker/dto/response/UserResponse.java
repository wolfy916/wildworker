package com.a304.wildworker.dto.response;

import com.a304.wildworker.common.Constants;
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
    private long titleId;
    private int titleType;
    private long coin;
    private int collectedPapers;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .characterType(user.getCharacterId().ordinal())
                .titleType(user.getTitleType().ordinal())
                .titleId(Constants.NONE_TITLE_ID)
//                .titleId(user.getTitle_id())    //TODO. get title_id to title name
                .coin(user.getBalance())
                .collectedPapers(user.getNumberOfCollectedPaper())
                .build();
    }
}
