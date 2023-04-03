package com.a304.wildworker.domain.match;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.MiniGameCode;
import com.a304.wildworker.domain.minigame.MiniGame;
import com.a304.wildworker.exception.AlreadySendException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Match {

    protected final String id;
    protected final List<ActiveUser> users;
    protected MiniGameCode miniGameCode;
    protected MiniGame miniGame;

    protected Map<Long, Integer> selected;  //게임 진행 선택(key: userId, value: 도망 여부) value - 0: fight, 1: run
    protected Map<Long, Integer> progress;  //미니 게임 개인 과정

    protected Match(String id, List<ActiveUser> users, MiniGameCode miniGameCode) {
        this.id = id;
        this.users = users;
        this.miniGameCode = miniGameCode;
//        this.miniGame = MiniGameFactory.findByCode(miniGameCode);
        selected = new ConcurrentHashMap<>();
        progress = new ConcurrentHashMap<>();
    }

    public abstract int getTimeLimitSec();

    public abstract int getCost();

    public abstract int getRunCost();

    public void addSelected(long userId, int selected) {
        if (this.selected.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.selected.put(userId, selected);
    }

    public void addProgress(long userId, int progress) {
        if (this.progress.containsKey(userId)) {
            throw new AlreadySendException();
        }
        this.progress.put(userId, progress);
    }

    public Long getWinner() {
        return miniGame.getWinner(progress);
    }

}
