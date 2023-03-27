package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.ethereum.helper.TransactionSendHelper;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;

@RequiredArgsConstructor
@Slf4j
@Component
public class StationContract {

    private final TransactionSendHelper transactionSendHelper;

    public CompletableFuture<Void> autoMine(String stationAddress, String userAddress, long amount)
            throws IOException {
        Function function = new Function("autoMine",
                List.of(new Address(userAddress), new Uint256(amount)),
                Collections.emptyList());

        return transactionSendHelper.sendContractAsync(stationAddress, function)
                .thenAccept(receipt -> log.info("autoMine : {}", receipt));
    }
}
