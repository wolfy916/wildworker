package com.a304.wildworker.dto.response;

import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.dto.response.MatchingResponse.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
public class MiniGameResultResponse {

    private boolean winner;
    private UserDto enemy;
    private ResultDto result;
    private ReceiptDto receipt;

    public static MiniGameResultResponse of(Match match, User me) {
        User enemy = match.getEnemy(me);
        ResultDto result = ResultDto.of(
                match.getPersonalProgress().get(me.getId()),
                match.getPersonalProgress().get(enemy.getId()));
        ReceiptDto receipt = ReceiptDto.of(match, me);
        return MiniGameResultResponse.builder()
                .winner(match.getWinner().equals(me.getId()))
                .enemy(UserDto.of(enemy))
                .result(result)
                .receipt(receipt)
                .build();
    }

    @Getter
    @ToString
    @AllArgsConstructor(staticName = "of")
    public static class ResultDto {

        private int me;
        private int enemy;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @Builder(access = AccessLevel.PROTECTED)
    public static class ReceiptDto {

        private long cost;
        private long runCost;
        private long reward;
        private long commission;
        private long total;

        public static ReceiptDto of(Match match, User me) {
            //TODO
            long cost = match.getCost();
            long runCost = match.getRunCostById(me.getId());
            long reward = match.getReward(me.getId());
            long commission = match.getCommission(me.getId());
            long total = cost - runCost + reward;

            return ReceiptDto.builder()
                    .cost(cost)
                    .runCost(runCost)
                    .reward(reward)
                    .commission(commission)
                    .total(total)
                    .build();
        }
    }

}
