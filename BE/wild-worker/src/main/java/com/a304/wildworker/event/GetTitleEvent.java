package com.a304.wildworker.event;

import com.a304.wildworker.domain.title.Title;
import com.a304.wildworker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetTitleEvent {

    private User user;
    private Title title;
}
