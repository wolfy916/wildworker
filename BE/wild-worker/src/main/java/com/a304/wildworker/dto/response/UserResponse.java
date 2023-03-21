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
    private String title;
    private long coin;
    private int collectedPapers;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .name(user.getName())
//                .title(user.getTitle_id())    //TODO. get title_id to title name
                .coin(user.getBalance())
                .collectedPapers(user.getNumberOfCollectedPaper())
                .build();
    }
}
