package com.a304.wildworker.event.handler;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.transaction.TransactionLog;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import com.a304.wildworker.dto.response.CoinChangeResponse;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.event.ChangedBalanceEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.CipherException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChangedBalanceHandler {

    private final Bank bank;

    private final TransactionLogRepository transactionLogRepository;
    private final ActiveUserRepository activeUserRepository;

    private final SimpMessagingTemplate messagingTemplate;

    /* 코인 변동 Noti 송신 */
    @Order(1)
    @EventListener
    public void sendCoinNotification(ChangedBalanceEvent event) {
        activeUserRepository.findById(event.getUser().getId()).ifPresent(activeUser -> {
            WSBaseResponse<CoinChangeResponse> response = WSBaseResponse.coin(event.getReason())
                    .data(new CoinChangeResponse(event.getUser().getBalance(),
                            event.getChangeValue()));

            messagingTemplate.convertAndSendToUser(activeUser.getWebsocketSessionId(),
                    "/queue",
                    response,
                    WebSocketUtils.createHeaders(activeUser.getWebsocketSessionId()));
        });
    }

    /* 코인내역 추가 & 이더리움에 내역 동기화 */
    @Transactional
    @Order(2)
    @EventListener
    public void insertCoinLogAndSyncWithEther(ChangedBalanceEvent event)
            throws CipherException, IOException {
        // 코인내역 추가
        TransactionLog transactionLog = new TransactionLog(
                event.getUser(),
                event.getStation(),
                event.getChangeValue(),
                event.getReason());
        transactionLogRepository.save(transactionLog);

        // 컨트랙트 실행
        switch (event.getReason()) {
            // 수동 채굴
            case MANUAL_MINING: {
                bank.manualMine(event.getUser()).thenAccept(t -> {
                    transactionLog.setAppliedAt(LocalDateTime.now());
                });
                break;
            }
            // 자동 채굴
            case AUTO_MINING: {
                bank.autoMine(event.getStation(), event.getUser()).thenAccept(t -> {
                    transactionLog.setAppliedAt(LocalDateTime.now());
                });
                break;
            }
            default:
                break;
        }
    }

}
