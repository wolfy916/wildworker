package com.a304.wildworker.service;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.ResultCode;
import com.a304.wildworker.domain.common.RunCode;
import com.a304.wildworker.domain.minigame.MiniGameLog;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import com.a304.wildworker.domain.title.Title;
import com.a304.wildworker.domain.title.TitleAwardedRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.event.GetTitleEvent;
import com.a304.wildworker.exception.TitleNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TitleService {

    private final UserRepository userRepository;
    private final MiniGameLogRepository miniGameLogRepository;
    private final TitleAwardedRepository titleAwardedRepository;
    private final TitleRepository titleRepository;

    private final ApplicationEventPublisher publisher;

    /* 칭호 조건 달성 시 획득 처리 */
    public void checkAndGetTitle(Long userId, Long titleId) {
        // 이미 획득한 경우
        if (alreadyGetTitle(userId, titleId)) {
            return;
        }

        boolean getTitle = false;
        // 승부사
        if (titleId == Constants.RISK_TAKER) {
            getTitle = checkGetRiskTaker(userId);
        }
        // 쫄보
        else if (titleId == Constants.RUNNER) {
            getTitle = checkGetRunner(userId);
        }
        // 무일푼
        else if (titleId == Constants.POOR) {
            getTitle = checkGetPoor(userId);
        }
        // 부자
        else if (titleId == Constants.RICH) {
            getTitle = checkGetRich(userId);
        }
        // 똥손
        else if (titleId == Constants.LOOSER) {
            getTitle = checkGetLooser(userId);
        }
        // 금손
        else if (titleId == Constants.WINNER) {
            getTitle = checkGetWinner(userId);
        }

        // 새 칭호 획득
        if (getTitle) {
            getTitle(userId, titleId);
        }
    }

    /* 승부사 칭호 */
    private boolean checkGetRiskTaker(Long userId) {
        // 최근 게임로그 가져오기
        List<MiniGameLog> gameLogList = getGameLogByCount(userId, Constants.CONDITION_RISK_TAKER);
        boolean getTitle = true;

        if (gameLogList.size() < Constants.CONDITION_RISK_TAKER) {
            getTitle = false;
        } else {
            for (MiniGameLog gameLog : gameLogList) {
                RunCode runCode = gameLog.getRunCode();

                // 둘 다 도망친 경우
                if (runCode == RunCode.ALL) {
                    getTitle = false;
                    break;
                }

                // 내가 user1 && user1이 도망친 경우
                if (gameLog.getUser1().getId().equals(userId)) {
                    if (runCode == RunCode.USER1) {
                        getTitle = false;
                        break;
                    }
                }
                // 내가 user2 && user2가 도망친 경우
                else {
                    if (runCode == RunCode.USER2) {
                        getTitle = false;
                        break;
                    }
                }
            }
        }

        return getTitle;
    }

    /* 쫄보 칭호 */
    private boolean checkGetRunner(Long userId) {
        // 최근 게임로그 가져오기
        List<MiniGameLog> gameLogList = getGameLogByCount(userId, Constants.CONDITION_RUNNER);
        boolean getTitle = true;

        if (gameLogList.size() < Constants.CONDITION_RUNNER) {
            getTitle = false;
        } else {
            for (MiniGameLog gameLog : gameLogList) {
                RunCode runCode = gameLog.getRunCode();

                // 둘 다 도망치지 않은 경우
                if (runCode == RunCode.NONE) {
                    getTitle = false;
                    break;
                }

                // 내가 user1 && user1이 도망치지 않은 경우
                if (gameLog.getUser1().getId().equals(userId)) {
                    if (runCode != RunCode.USER1) {
                        getTitle = false;
                        break;
                    }
                }
                // 내가 user2 && user2가 도망치지 않은 경우
                else {
                    if (runCode != RunCode.USER2) {
                        getTitle = false;
                        break;
                    }
                }
            }
        }

        return getTitle;
    }

    /* 무일푼 칭호 */
    private boolean checkGetPoor(Long userId) {
        User user = getUserOrElseThrow(userId);

        boolean getTitle = true;
        if (user.getBalance() > Constants.CONDITION_POOR) {
            getTitle = false;
        }

        return getTitle;
    }

    /* 부자 칭호 */
    private boolean checkGetRich(Long userId) {
        User user = getUserOrElseThrow(userId);

        boolean getTitle = true;
        if (user.getBalance() < Constants.CONDITION_RICH) {
            getTitle = false;
        }

        return getTitle;
    }

    /* 똥손 칭호 */
    private boolean checkGetLooser(Long userId) {
        // 최근 게임로그 가져오기
        List<MiniGameLog> gameLogList = getGameLogByCount(userId, Constants.CONDITION_LOOSER);
        boolean getTitle = true;

        if (gameLogList.size() < Constants.CONDITION_LOOSER) {
            getTitle = false;
        } else {
            for (MiniGameLog gameLog : gameLogList) {
                ResultCode resultCode = gameLog.getResultCode();

                // 게임이 성사되지 않은 경우
                if (resultCode == ResultCode.NONE) {
                    getTitle = false;
                    break;
                }

                // 내가 user1 && user1이 승리한 경우
                if (gameLog.getUser1().getId().equals(userId)) {
                    if (resultCode == ResultCode.WIN_USER1) {
                        getTitle = false;
                        break;
                    }
                }
                // 내가 user2 && user2가 승리한 경우
                else {
                    if (resultCode == ResultCode.WIN_USER2) {
                        getTitle = false;
                        break;
                    }
                }
            }
        }

        return getTitle;
    }

    /* 금손 칭호 */
    private boolean checkGetWinner(Long userId) {
        // 최근 게임로그 가져오기
        List<MiniGameLog> gameLogList = getGameLogByCount(userId, Constants.CONDITION_WINNER);
        boolean getTitle = true;

        if (gameLogList.size() < Constants.CONDITION_WINNER) {
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

    /* count 만큼 최근 게임로그 반환 */
    private List<MiniGameLog> getGameLogByCount(Long userId, int count) {
        PageRequest pageRequest = PageRequest.of(0, count);
        return miniGameLogRepository.findByUser1IdOrUser2IdOrderByCreatedAtDesc(userId, userId,
                pageRequest).getContent();
    }

    /* 칭호 보유 여부 확인 */
    public boolean alreadyGetTitle(Long userId, Long titleId) {
        if (titleAwardedRepository.existsByTitleIdAndUserId(titleId, userId)) {
            return true;
        }
        return false;
    }

    /* 새 칭호 획득 */
    private void getTitle(Long userId, Long titleId) {
        User user = getUserOrElseThrow(userId);
        Title title = getTitleOrElseThrow(titleId);

        // 칭호 획득 이벤트 발생
        publisher.publishEvent(new GetTitleEvent(user, title));
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Title getTitleOrElseThrow(Long titleId) {
        return titleRepository.findById(titleId)
                .orElseThrow(TitleNotFoundException::new);
    }
}
