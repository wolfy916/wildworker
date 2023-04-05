package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.MatchProgress;
import com.a304.wildworker.domain.common.ResultCode;
import com.a304.wildworker.domain.common.RunCode;
import com.a304.wildworker.domain.match.strategy.DuelStrategy;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.MatchCancelEvent;
import com.a304.wildworker.event.MatchSelectEndEvent;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.MiniGameEndEvent;
import com.a304.wildworker.event.MiniGameStartEvent;
import com.a304.wildworker.event.common.Events;
import com.a304.wildworker.exception.AlreadySendException;
import com.a304.wildworker.exception.NotInProgressException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public abstract class Match {

    protected final String id;

    protected final long stationId;
    protected final List<User> users;
    @Setter
    protected MiniGame miniGame;
    protected Map<Long, Duel> selected;  //게임 진행 선택(key: userId, value: 도망 여부) value - 0: fight, 1: run
    protected Map<Long, Integer> personalProgress;  //미니 게임 개인 과정
    private MatchProgress progress;
    private double commissionRate;
    @Setter
    private DuelStrategy duelStrategy;

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

    private long getTotalCost() {
        long totalCost = getCost() * users.size();
        long totalRunCost = getRunCost() * Integer.bitCount(getRunCode().ordinal());
        return totalCost + totalRunCost;
    }

    public long getCommission(long userId) {

        return (long) Math.ceil(this.getTotalCost() * commissionRate / 100);
    }

    public long getReward(long userId) {
        return getTotalCost() - getCommission(userId);
    }

    public void changeProgress(MatchProgress progress) {
        this.progress = progress;
        switch (progress) {
            case MATCHING:
                Events.raise(MatchingSuccessEvent.of(this));
                break;
            case CANCEL:
                Events.raise(MatchCancelEvent.of(this));
                break;
            case MINIGAME_START:
                Events.raise(MiniGameStartEvent.of(this));
                break;
            case RESULT:
                Events.raise(MiniGameEndEvent.of(this));
                break;
        }
    }

    private void checkProgress(MatchProgress request) {
        if (this.progress != request) {
            throw new NotInProgressException(this.progress, request);
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
        checkProgress(MatchProgress.SELECTING_START);

        if (this.selected.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.selected.put(userId, selected);
    }

    public void endSelectingProgress() {
        checkProgress(MatchProgress.SELECTING_START);
        this.progress = MatchProgress.SELECTING_END;

        for (User user : users) {
            if (!selected.containsKey(user.getId())) {
                selected.put(user.getId(), Duel.RUN);
            }
        }

        if (decideDuel(getRunCode()) == Duel.DUEL) {
            changeProgress(MatchProgress.MINIGAME_START);
        } else {
            changeProgress(MatchProgress.CANCEL);
        }

        Events.raise(MatchSelectEndEvent.of(this)); //pay run cost
    }

    public void addPersonalProgress(long userId, int progress) {
        checkProgress(MatchProgress.MINIGAME_START);
        if (this.personalProgress.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.personalProgress.put(userId, progress);
    }

    public void endMiniGameProgress() {
        checkProgress(MatchProgress.MINIGAME_START);
        this.progress = MatchProgress.MINIGAME_END;

        for (User user : users) {
            if (!personalProgress.containsKey(user.getId())) {
                personalProgress.put(user.getId(), 0);
            }
        }

        changeProgress(MatchProgress.RESULT);
    }

    public Long getWinner() {
        return miniGame.getWinner(personalProgress);
    }


    /////// for User ////
    public User getEnemy(User me) {
        int myIdx = users.indexOf(me);
        if (myIdx < 0) {
            throw new UserNotFoundException();
        }
        return users.get((myIdx + 1) % users.size());
    }

    public boolean isRunner(Long userId) {
        return Optional.ofNullable(selected.get(userId)).orElse(Duel.RUN) == Duel.RUN;
    }

    public boolean isWinner(long userId) {
        return getWinner() == userId;
    }

    public long getRunCostById(long userId) {
        return isRunner(userId) ? this.getRunCost() : 0;
    }

//    public long getRewardById(long userId, double commission) {
//        return isWinner(userId) ? this.getReward(commission) : 0;
//    }
}
