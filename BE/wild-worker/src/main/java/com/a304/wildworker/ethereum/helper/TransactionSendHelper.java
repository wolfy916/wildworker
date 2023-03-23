package com.a304.wildworker.ethereum.helper;

import java.io.IOException;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

@Slf4j
@Component
public class TransactionSendHelper {

    private final Web3j web3j;
    private final TransactionManager transactionManager;
    private final Long chainId = 1337L;

    public TransactionSendHelper(Web3j web3j, Credentials root) {
        this.web3j = web3j;
        this.transactionManager = new RawTransactionManager(web3j, root, chainId);
    }

    public void sendContract(String contractAddress, Function function) throws IOException {
        Block block = web3j.ethGetBlockByNumber(
                        DefaultBlockParameterName.LATEST, false)
                .send()
                .getBlock();

        BigInteger gasLimit = block.getGasLimit();
        BigInteger baseFeePerGas = Numeric.decodeQuantity(block.getBaseFeePerGas());
        BigInteger amountUsed = baseFeePerGas;

        EthSendTransaction ethSendTransaction = transactionManager.sendEIP1559Transaction(
                chainId,
                amountUsed,
                baseFeePerGas,
                gasLimit,
                contractAddress,
                FunctionEncoder.encode(function), null);

        EthGetTransactionReceipt result = getGetTransactionReceipt(ethSendTransaction);

        log.info("transaction receipt: {}", result.getResult());
    }

    private EthGetTransactionReceipt getGetTransactionReceipt(EthSendTransaction ethSendTransaction)
            throws IOException {
        return web3j.ethGetTransactionReceipt(ethSendTransaction.getTransactionHash()).send();
    }

}
