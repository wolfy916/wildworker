package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.ethereum.helper.TransactionSendHelper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

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

        transactionSendHelper.sendContract(this.address, function);
    }
}
