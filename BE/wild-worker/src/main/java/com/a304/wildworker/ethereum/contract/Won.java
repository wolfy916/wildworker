package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.ethereum.helper.TransactionSendHelper;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

/**
 * Won 컨트랙트에 요청을 보내는 객체
 */
@Slf4j
@Component
public class Won {

    public Won(@Value("${web3.contract.won.address}") String address,
            TransactionSendHelper transactionSendHelper) {
        this.address = address;
        this.transactionSendHelper = transactionSendHelper;
    }

    private final String address;
    private final TransactionSendHelper transactionSendHelper;

    public void manualMine(String userAddress, long amount) throws IOException {

        Function function = new Function("manualMine",
                Arrays.asList(new Address(userAddress), new Uint256(amount)),
                Collections.emptyList());

        TransactionReceipt transactionReceipt = transactionSendHelper.sendContract(this.address,
                function);
        log.info("manualMine result : {}", transactionReceipt);
    }

    /**
     * @param userAddress 잔액을 확인할 사용자의 지갑 주소
     * @return BigInteger 잔액(단위 won)
     * @throws IOException
     */
    public BigInteger balanceOf(String userAddress) throws IOException {
        Function function = new Function("balanceOf",
                Arrays.asList(new Address(userAddress)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));

        String callResult = transactionSendHelper.sendCall(this.address, function);
        return Numeric.decodeQuantity(callResult);
    }
}
