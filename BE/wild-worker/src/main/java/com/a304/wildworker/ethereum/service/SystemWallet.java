package com.a304.wildworker.ethereum.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

/**
 * System 지갑
 */
@Slf4j
@Component
public class SystemWallet {

    private final Web3j web3j;
    private final Credentials credentials;

    public SystemWallet(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        this.credentials = credentials;
    }

    /**
     * 트랜잭션 실행 시 가스비가 모자랄 경우 이 메소드를 호출하여 가스비를 지원받을 수 있다.
     *
     * @param address 가스비를 전달 받을 주소
     */
    public void sendGasTo(String address)
            throws Exception {
        TransactionReceipt send = Transfer.sendFunds(web3j,
                credentials, address, BigDecimal.valueOf(1), Unit.ETHER).send();
        log.info("send gas :: {}", send);
    }

}
