package com.a304.wildworker.domain.title.strategy;

import com.a304.wildworker.domain.common.TitleCode;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;

public class RichStrategy extends TitleStrategy {

    public RichStrategy(UserRepository userRepository, TitleRepository titleRepository,
            MiniGameLogRepository miniGameLogRepository) {
        super(userRepository, titleRepository, miniGameLogRepository);
    }

    public boolean checkTitle(Long userId) {
        User user = getUserOrElseThrow(userId);

        boolean getTitle = true;
        if (user.getBalance() < TitleCode.RICH.getCondition()) {
            getTitle = false;
        }

        return getTitle;
    }
}
