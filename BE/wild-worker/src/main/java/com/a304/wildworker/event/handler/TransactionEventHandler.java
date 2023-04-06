package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.TransactionEvent;
import com.a304.wildworker.service.BankService;
import com.a304.wildworker.service.StationService;
import com.a304.wildworker.service.TransactionLogService;
import com.a304.wildworker.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionEventHandler {

    private final TransactionLogService transactionLogService;
    private final UserTransactionService userTransactionService;
    private final StationService stationService;
    private final BankService bankService;

    @Async
    @Transactional
    @EventListener
    public void handle(TransactionEvent event) throws Exception {
        log.info("event occur: TransactionEvent - {}", event.toString());
        long value = event.getValue();
        if (value == 0) {
            return;
        }

        TransactionType type = event.getType();
        Station station = event.getStation();
        User user = event.getUser();

        userTransactionService.changeBalance(user.getId(), value);
        switch (type) {
            case MINI_GAME_COST:
            case MINI_GAME_REWARD:
            case MINI_GAME_RUN_COST:
                stationService.changeCommission(station.getId(), -value);
                break;
        }
        transactionLogService.create(type, station, user, value);
        bankService.sendTransaction(type, station, user, Math.abs(value));
    }

}
