package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.MatchStatus;
import com.a304.wildworker.domain.common.PersonalWinCode;
import com.a304.wildworker.domain.common.ResultCode;
import com.a304.wildworker.domain.common.RunCode;
import com.a304.wildworker.domain.match.strategy.DuelStrategy;
import com.a304.wildworker.domain.match.strategy.WinnerStrategy;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.MatchCancelEvent;
import com.a304.wildworker.event.MatchSelectEndEvent;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.MiniGameEndEvent;
import com.a304.wildworker.event.MiniGameStartEvent;
import com.a304.wildworker.event.common.Events;
import com.a304.wildworker.exception.AlreadySendException;
import com.a304.wildworker.exception.NotInMatchProgressException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public abstract class Match {

    protected final String id;

    protected final long stationId;
    protected final List<User> users;
    @Setter
    protected MiniGame miniGame;
    protected double commissionRate;
    protected Map<Long, Duel> selected;  //게임 진행 선택(key: userId, value: 도망 여부) value - 0: fight, 1: run
    protected Map<Long, Integer> personalProgress;  //미니 게임 개인 과정
    protected MatchStatus status;
    @Setter
    protected DuelStrategy duelStrategy;
    @Setter
    protected WinnerStrategy winnerStrategy;


    protected Match(String id, long stationId, List<User> users) {
        this.id = id;
        this.stationId = stationId;
        this.users = users;
        selected = new ConcurrentHashMap<>();
        personalProgress = new ConcurrentHashMap<>();
    }

    public abstract int getTimeLimitSec();

    public abstract long getRunCost();

    public abstract long getCost();

    public ResultCode getResultCode() {
        return ResultCode.NONE;     //TODO
    }

    public void changeProgress(MatchStatus progress) {
        this.status = progress;
        switch (progress) {
            case MATCHING:
                log.info("change game progress(MATCHING)");
                Events.raise(MatchingSuccessEvent.of(this));
                break;
            case CANCEL:
                log.info("change game progress(CANCEL)");
                Events.raise(MatchCancelEvent.of(this));
                break;
            case MINIGAME_START:
                log.info("change game progress(MINIGAME_START)");
                Events.raise(MiniGameStartEvent.of(this));
                break;
            case RESULT:
                log.info("change game progress(RESULT)");
                Events.raise(MiniGameEndEvent.of(this));
                break;
        }
    }

    private void checkProgress(MatchStatus request) {
        if (this.status != request) {
            throw new NotInMatchProgressException(this.status, request);
        }
    }

    public int getUserIndexById(long userId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)) {
                return i;
            }
        }
        throw new UserNotFoundException();
    }

    public RunCode getRunCode() {
        AtomicInteger runCode = new AtomicInteger();
        selected.forEach((k, v) -> {
            if (v == Duel.RUN) {
                int idx = getUserIndexById(k);
                runCode.addAndGet(1 << idx);
            }
        });
        return RunCode.fromOrdinary(runCode.get());
    }

    private Duel decideDuel(RunCode runCode) {
        return duelStrategy.decideDuel(runCode);
    }

    public void addSelected(long userId, Duel selected) {
        checkProgress(MatchStatus.SELECTING_START);

        if (this.selected.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.selected.put(userId, selected);
    }

    public void endSelectingProgress() {
        log.info("게임 진행 선택 완료 후처리 {}", selected.size());
        checkProgress(MatchStatus.SELECTING_START);
        this.status = MatchStatus.SELECTING_END;

        for (User user : users) {
            if (!selected.containsKey(user.getId())) {
                selected.put(user.getId(), Duel.RUN);
            }
        }

        if (decideDuel(getRunCode()) == Duel.DUEL) {
            changeProgress(MatchStatus.MINIGAME_START);
        } else {
            changeProgress(MatchStatus.CANCEL);
        }

        Events.raise(MatchSelectEndEvent.of(this)); //pay run cost
    }

    public void addPersonalProgress(long userId, int progress) {
        checkProgress(MatchStatus.MINIGAME_START);
        if (this.personalProgress.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.personalProgress.put(userId, progress);
    }

    public void endMiniGameProgress() {
        checkProgress(MatchStatus.MINIGAME_START);
        this.status = MatchStatus.MINIGAME_END;

        for (User user : users) {
            if (!personalProgress.containsKey(user.getId())) {
                personalProgress.put(user.getId(), 0);
            }
        }

        changeProgress(MatchStatus.RESULT);
    }

    /////// for User ////

    public Long getWinner() {
        return winnerStrategy.getWinner(personalProgress);
    }

    public boolean isRunner(Long userId) {
        return Optional.ofNullable(selected.get(userId)).orElse(Duel.RUN) == Duel.RUN;
    }

    public PersonalWinCode isWinner(long userId) {
        Long winner = getWinner();
        if (winner == null) {
            return PersonalWinCode.DRAW;
        } else if (winner == userId) {
            return PersonalWinCode.WIN;
        } else {
            return PersonalWinCode.LOSE;
        }
    }

    public User getEnemy(User me) {
        int myIdx = users.indexOf(me);
        if (myIdx < 0) {
            throw new UserNotFoundException();
        }
        return users.get((myIdx + 1) % users.size());
    }

    /**
     * 사용자가 획득한 환급비(수수료 차감 전)
     *
     * @param userId
     * @return >= 0
     */
    public long getReward(long userId) {
        long cost = getCost();
        switch (isWinner(userId)) {
            case DRAW:
                return -cost;
            case WIN:
                long totalCost = -cost * users.size();
                long totalRunCost = -getRunCost() * Integer.bitCount(getRunCode().ordinal());
                return totalCost + totalRunCost;
        }
        return 0;
    }

    /**
     * 환급비에서 제외될 수수료 금액
     *
     * @param userId
     * @return <= 0
     */
    public long getCommission(long userId) {
        if (isWinner(userId) == PersonalWinCode.WIN) {
            return (long) -Math.ceil(this.getReward(userId) * commissionRate / 100);
        }
        return 0;
    }

    public long getRunCostById(long userId) {
        return isRunner(userId) ? this.getRunCost() : 0;
    }
}
