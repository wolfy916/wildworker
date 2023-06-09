package com.a304.wildworker.ethereum.helper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

@Slf4j
@Component
public class TransactionSendHelper {

    private final Web3j web3j;
    private final String senderAddress;
    private final TransactionManager transactionManager;
    private final Long chainId;

    public TransactionSendHelper(Web3j web3j, Credentials root,
            @Value("${web3.chain-id}") Long chainId) {
        this.web3j = web3j;
        this.senderAddress = root.getAddress();
        this.chainId = chainId;
        this.transactionManager = new RawTransactionManager(web3j, root, this.chainId);
    }

    /**
     * 블록체인 네트워크에 동기 요청 보내는 메서드
     *
     * @param contractAddress 요청할 컨트랙트의 주소
     * @param function        요청할 컨트랙트의 내용 (abi 기준으로 작성)
     * @return String transactionHash 트랜잭션의 해시값을 반환
     * @throws IOException
     */
    public TransactionReceipt sendContract(String contractAddress, Function function)
            throws IOException {
        Block block = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send()
                .getBlock();

        BigInteger gasLimit = block.getGasLimit();
        BigInteger baseFeePerGas = Numeric.decodeQuantity(block.getBaseFeePerGas());
        BigInteger amountUsed = baseFeePerGas; // TODO: 2023-03-23 값 조정 필요

        EthSendTransaction ethSendTransaction = transactionManager.sendEIP1559Transaction(
                chainId,
                amountUsed,
                baseFeePerGas,
                gasLimit,
                contractAddress,
                FunctionEncoder.encode(function), null);

        return getTransactionReceipt(ethSendTransaction);
    }

    /**
     * 블록체인 네트워크에 비동기 요청 보내는 메서드 예외 발생 시 RuntimeException 발생
     *
     * @param contractAddress
     * @param function
     * @return
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> sendContractAsync(String contractAddress,
            Function function)
            throws IOException {
        return this.sendContractAsync(contractAddress, senderAddress, function);
    }

    /**
     * 블록체인 네트워크에 비동기 요청 보내는 메서드 예외 발생 시 RuntimeException 발생
     *
     * @param contractAddress
     * @param fromAddress     msg.sender에 매핑될 주소
     * @param function
     * @return
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> sendContractAsync(String contractAddress,
            String fromAddress,
            Function function)
            throws IOException {
        log.debug("transaction start");
        log.debug("\tcontract : {}", contractAddress);
        log.debug("\tsender : {}", fromAddress);

        return getBlock().thenCombine(getNonce(fromAddress), (block, nonce) -> {
            BigInteger gasLimit = block.getGasLimit();
            BigInteger baseFeePerGas = Numeric.decodeQuantity(block.getBaseFeePerGas());
            BigInteger amountUsed = baseFeePerGas; // TODO: 2023-03-23 값 조정 필요

            try {
                log.debug("\t\ttransaction sending");
                return web3j.ethSendTransaction(
                                Transaction.createFunctionCallTransaction(
                                        fromAddress,
                                        nonce,
                                        amountUsed,
                                        gasLimit,
                                        contractAddress,
                                        FunctionEncoder.encode(function)
                                ))
                        .sendAsync()
                        .thenApply(ethSendTransaction -> {
                                    TransactionReceipt transactionReceipt;
                                    try {
                                        transactionReceipt = getTransactionReceipt(ethSendTransaction);
                                        if (ethSendTransaction.hasError()) {
                                            transactionReceipt = getDefaultTransactionReceipt(
                                                    contractAddress, fromAddress, block, nonce,
                                                    amountUsed, ethSendTransaction,
                                                    ethSendTransaction.getError().getMessage());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        transactionReceipt = getDefaultTransactionReceipt(contractAddress,
                                                fromAddress, block, nonce, amountUsed,
                                                ethSendTransaction, e.getMessage());
                                    }
                                    log.debug("\t\ttransaction receipt : {}", transactionReceipt);
                                    log.debug("transaction end");
                                    return transactionReceipt;
                                }
                        ).get(); // TODO: 2023-03-27 내부 로직 수정 필요
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }

    private TransactionReceipt getDefaultTransactionReceipt(String contractAddress,
            String fromAddress, Block block, BigInteger nonce, BigInteger amountUsed,
            EthSendTransaction ethSendTransaction, String message) {
        return new TransactionReceipt(
                ethSendTransaction.getTransactionHash(),
                Numeric.encodeQuantity(nonce),
                block.getHash(),
                block.getNumberRaw(),
                block.getGasUsedRaw(),
                Numeric.encodeQuantity(amountUsed),
                contractAddress,
                null, null,
                fromAddress,
                contractAddress,
                null,
                null,
                message,
                null,
                null);
    }

    private CompletableFuture<Block> getBlock() {
        return web3j.ethGetBlockByNumber(
                DefaultBlockParameterName.LATEST, false).sendAsync().thenApply(EthBlock::getBlock);
    }

    private CompletableFuture<BigInteger> getNonce(String address) {
        return web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync()
                .thenApply(EthGetTransactionCount::getTransactionCount);
    }

    /**
     * @param contractAddress 조회할 자료의 컨트랙트 주소
     * @param function        getter 메소드의 형식
     * @return String 결과값
     * @throws IOException
     */
    public String sendCall(String contractAddress, Function function) throws IOException {
        return transactionManager.sendCall(
                contractAddress,
                FunctionEncoder.encode(function), DefaultBlockParameterName.LATEST);
    }

    private TransactionReceipt getTransactionReceipt(EthSendTransaction ethSendTransaction)
            throws IOException {
        return web3j.ethGetTransactionReceipt(ethSendTransaction.getTransactionHash()).send()
                .getResult();
    }

}
