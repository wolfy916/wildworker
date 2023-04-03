package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.common.MatchProgress;
import com.a304.wildworker.domain.common.RunCode;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.event.MatchCancelEvent;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.Events;
import com.a304.wildworker.exception.AlreadySendException;
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
    protected final List<ActiveUser> users;
    @Setter
    protected MiniGame miniGame;
    protected Map<Long, Duel> selected;  //게임 진행 선택(key: userId, value: 도망 여부) value - 0: fight, 1: run
    protected Map<Long, Integer> personalProgress;  //미니 게임 개인 과정
    private MatchProgress progress;

    protected Match(String id, long stationId, List<ActiveUser> users) {
        this.id = id;
        this.stationId = stationId;
        this.users = users;
        selected = new ConcurrentHashMap<>();
        personalProgress = new ConcurrentHashMap<>();
    }

    public abstract int getTimeLimitSec();

    public abstract int getCost();

    public abstract int getRunCost();

    public void changeProgress(MatchProgress progress) {
        this.progress = progress;
        switch (progress) {
            case MATCHING:
                Events.raise(MatchingSuccessEvent.of(this));
                break;
            case SELECTING:
                //can receive selected
                // pay run cost
                break;
            case CANCEL:
                Events.raise(MatchCancelEvent.of(this));
                break;
            case START:
                //TODO: game start event
                // pay cost
                // can receive personal result
                break;
            case END:
                //TODO: game end event
                // send result
                // cal winner
                // cal reward, commission
                // create db log
                break;
        }
    }

    public void addSelected(long userId, Duel selected) {
        if (progress != MatchProgress.SELECTING) {
            throw new RuntimeException(); //TODO
        }
        if (this.selected.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.selected.put(userId, selected);
    }

    public void endSelected() {
        if (progress != MatchProgress.SELECTING) {
            throw new RuntimeException(); //TODO
        }
        for (ActiveUser user : users) {
            if (!selected.containsKey(user.getUserId())) {
                addSelected(user.getUserId(), Duel.RUN);
            }
        }
        if (isDual(getRunCode()) == Duel.DUEL) {
            changeProgress(MatchProgress.START);
        } else {
            changeProgress(MatchProgress.CANCEL);
        }
    }

    public RunCode getRunCode() {
        AtomicInteger runCode = new AtomicInteger();
        selected.forEach((k, v) -> {
            if (v == Duel.RUN) {
                int idx = users.indexOf(k);
                runCode.addAndGet(1 << idx);
            }
        });
        int runCodeIdx = runCode.get();
        return RunCode.fromOrdinary(runCodeIdx);
    }

    private Duel isDual(RunCode runCode) {
        if (runCode == RunCode.NONE) {
            return Duel.DUEL;
        } else if (runCode == RunCode.ALL) {
            return Duel.RUN;
        } else {
            return Duel.random();
        }
    }

    public void addProgress(long userId, int progress) {
        if (this.personalProgress.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.personalProgress.put(userId, progress);
    }

    public Long getWinner() {
        return miniGame.getWinner(personalProgress);
    }

    public ActiveUser getEnemy(int myIdx) {
        return users.get((myIdx + 1) % users.size());
    }

    public boolean isRunner(Long userId) {
        return Optional.ofNullable(selected.get(userId)).orElse(Duel.RUN) == Duel.RUN;
    }
}
