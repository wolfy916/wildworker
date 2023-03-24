package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.ethereum.helper.TransactionSendHelper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@RequiredArgsConstructor
@Slf4j
@Component
public class StationContract {

    private final TransactionSendHelper transactionSendHelper;

    public void autoMine(String stationAddress, String userAddress, long amount)
            throws IOException {
        Function function = new Function("autoMine",
                List.of(new Address(userAddress), new Uint256(amount)),
                Collections.emptyList());

        TransactionReceipt receipt = transactionSendHelper.sendContract(stationAddress,
                function);

        log.info("autoMine : {}", receipt);
        log.info("revert : {}", receipt.getRevertReason());
    }
}
