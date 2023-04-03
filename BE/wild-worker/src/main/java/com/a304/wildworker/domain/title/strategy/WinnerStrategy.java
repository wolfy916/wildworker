package com.a304.wildworker.domain.title.strategy;

import com.a304.wildworker.domain.common.ResultCode;
import com.a304.wildworker.domain.common.TitleCondition;
import com.a304.wildworker.domain.minigame.MiniGameLog;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.UserRepository;
import java.util.List;

public class WinnerStrategy extends TitleStrategy {

    public WinnerStrategy(UserRepository userRepository, TitleRepository titleRepository,
            MiniGameLogRepository miniGameLogRepository) {
        super(userRepository, titleRepository, miniGameLogRepository);
    }

    public boolean checkTitle(Long userId) {
        // 최근 게임로그 가져오기
        List<MiniGameLog> gameLogList = getGameLogByCount(userId, TitleCondition.WINNER.getValue());
        boolean getTitle = true;

        if (gameLogList.size() < TitleCondition.WINNER.getValue()) {
            getTitle = false;
        } else {
            for (MiniGameLog gameLog : gameLogList) {
                ResultCode resultCode = gameLog.getResultCode();

                // 게임이 성사되지 않은 경우
                if (resultCode == ResultCode.NONE) {
                    getTitle = false;
                    break;
                }

                // 내가 user1 && user1이 승리하지 않은 경우
                if (gameLog.getUser1().getId().equals(userId)) {
                    if (resultCode != ResultCode.WIN_USER1) {
                        getTitle = false;
                        break;
                    }
                }
                // 내가 user2 && user2가 승리하지 않은 경우
                else {
                    if (resultCode != ResultCode.WIN_USER2) {
                        getTitle = false;
                        break;
                    }
                }
            }
        }

        return getTitle;
    }
}
