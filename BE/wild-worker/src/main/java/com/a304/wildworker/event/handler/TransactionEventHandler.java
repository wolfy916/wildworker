package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.event.TransactionEvent;
import com.a304.wildworker.service.BankService;
import com.a304.wildworker.service.TransactionLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransactionEventHandler {

    private final TransactionLogService transactionLogService;
    private final BankService bankService;

    @EventListener
    public void handle(TransactionEvent event) throws Exception {
        TransactionType type = event.getType();
        Station station = event.getStation();
        User user = event.getUser();
        long value = event.getValue();

        station.changeCommission(value);
        user.changeBalance(value);
        transactionLogService.create(type, station, user, value);
        bankService.banking(type, station, user, Math.abs(value));
    }

}
