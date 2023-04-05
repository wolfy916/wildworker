package com.a304.wildworker.ethereum.config;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;

@Component
@Slf4j
public class RootNonce {

    private final AtomicLong instance;

    /*
     *   모든 트랜잭션이 반드시 스프링의 web3j 빈을 통해 실행되어야 nonce 값이 유지됨
     * */

    public RootNonce(Web3j web3j) throws IOException {
        long transactionCountOfRoot = web3j.ethGetTransactionCount(
                web3j.ethAccounts().send().getAccounts().get(0),
                DefaultBlockParameterName.LATEST).send().getTransactionCount().longValue();
        this.instance = new AtomicLong(transactionCountOfRoot);
    }

    public BigInteger getNonce() {
        log.info("current nonce is : {}", instance.get());
        return BigInteger.valueOf(instance.incrementAndGet());
    }
}
