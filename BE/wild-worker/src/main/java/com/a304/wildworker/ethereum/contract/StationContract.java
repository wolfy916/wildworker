package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.ethereum.helper.TransactionSendHelper;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

@RequiredArgsConstructor
@Slf4j
@Component
public class StationContract {

    private final TransactionSendHelper transactionSendHelper;

    public CompletableFuture<TransactionReceipt> autoMine(String stationAddress, String userAddress,
            long amount)
            throws IOException {
        log.info("autoMine call");
        log.info("\t from station : {}", stationAddress);
        log.info("\t to user : {}", userAddress);

        Function function = new Function("autoMine",
                List.of(new Address(userAddress), new Uint256(amount)), Collections.emptyList());

        return transactionSendHelper.sendContractAsync(stationAddress, function);
    }

    public CompletableFuture<TransactionReceipt> invest(String stationAddress, String userAddress,
            long amount)
            throws IOException {
        log.info("invest call");
        log.info("\t from user : {}", userAddress);
        log.info("\t to station : {}", stationAddress);
        log.info("\t amount : {}", amount);

        Function function = new Function("recordInvestment", List.of(new Uint256(amount)),
                Collections.emptyList());

        return transactionSendHelper.sendContractAsync(stationAddress, userAddress, function);
    }

    public CompletableFuture<TransactionReceipt> distributeInvestReward(String stationAddress,
            Long currentCommissionOfStation) throws IOException {
        Function function = new Function("countChargeEvery10Min",
                List.of(new Uint256(currentCommissionOfStation)), Collections.emptyList());

        return transactionSendHelper.sendContractAsync(stationAddress, function);
    }

    public BigInteger getInvestmentOfUser(String stationAddress, String userAddress)
            throws IOException {
        Function function = new Function("investors",
                List.of(new Address(userAddress)),
                List.of(new TypeReference<Uint>() {
                }));
        String result = transactionSendHelper.sendCall(stationAddress, function);

        return Numeric.decodeQuantity(result);
    }

    public BigInteger getInvestmentAmount(String stationAddress)
            throws IOException {
        Function function = new Function("investmentAmount",
                Collections.emptyList(),
                List.of(new TypeReference<Uint>() {
                }));
        String result = transactionSendHelper.sendCall(stationAddress, function);

        return Numeric.decodeQuantity(result);
    }

}
