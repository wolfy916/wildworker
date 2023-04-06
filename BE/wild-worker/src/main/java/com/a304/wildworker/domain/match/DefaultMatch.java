package com.a304.wildworker.domain.match;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.strategy.DefaultDuelStrategy;
import com.a304.wildworker.domain.match.strategy.MaximumInTwoPlayWinnerStrategy;
import com.a304.wildworker.domain.user.User;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

/*
 * DefaultMatch
 * • 랜덤 게임
 * • 리그별 고정 배팅 금액
 *      ◦ 최상위 : 20,000원 ◦ 상위 : 2,000원 ◦ 중위 :  200원 ◦ 하위 :  20원
 * • 리그별 고정 도망비
 *      ◦ 최상위 : 5,000원 ◦ 상위 : 500원 ◦ 중위 :  50원 ◦ 하위 :  5원
 */
@Getter
public class DefaultMatch extends Match {

    public static final int SELECT_TIME_LIMIT_SEC = 10;
    public static final long DEFAULT_COST = -20;
    public static final long DEFAULT_RUN_COST = -5;
    private final League league;

    public DefaultMatch(List<User> users, long stationId, League league) {
        super(UUID.randomUUID().toString(), stationId, users);
        this.league = league;
        this.setDuelStrategy(new DefaultDuelStrategy());
        this.winnerStrategy = new MaximumInTwoPlayWinnerStrategy();
        this.commissionRate = Constants.COMMISSION_RATE;
    }

    @Override
    public int getTimeLimitSec() {
        return SELECT_TIME_LIMIT_SEC;
    }

    public long getRunCost() {
        return DEFAULT_RUN_COST * (int) (Math.pow(10, league.ordinal() - 1));
    }

    public long getCost() {
        return DEFAULT_COST * (int) (Math.pow(10, league.ordinal() - 1));
    }

}
