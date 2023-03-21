package com.a304.wildworker.ethereum.service;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

@Slf4j
@Component
public class RootWallet {

    private final Web3j web3j;
    private final Credentials credentials;
    private final TransactionManager transactionManager;

    public RootWallet(Web3j web3j, Credentials credentials) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.transactionManager = new RawTransactionManager(web3j, credentials);
    }

    public void sendGasTo(String address)
            throws Exception {
        TransactionReceipt send = Transfer.sendFunds(web3j,
                credentials, address, BigDecimal.valueOf(1), Unit.ETHER).send();
        log.info("send gas :: {}", send);
    }

}
