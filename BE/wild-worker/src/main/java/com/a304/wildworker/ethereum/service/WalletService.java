package com.a304.wildworker.ethereum.service;

import com.a304.wildworker.ethereum.exception.InvalidAddressException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

@Service
public class WalletService {

    private final Web3j web3j;

    public WalletService(Web3j web3j) {
        this.web3j = web3j;
    }

    public String createWorkerWallet(
            String walletPassword) // walletPassword를 통해 내부적으로 privateKey를 생성해서 사용
            throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException {

        File defaultDirectory = new File(WalletUtils.getDefaultKeyDirectory());

        return WalletUtils.generateLightNewWalletFile(walletPassword, defaultDirectory);
    }

    public EthGetBalance getEthBalance(String address) throws IOException {
        if (!WalletUtils.isValidAddress(address)) {
            throw new InvalidAddressException();
        }
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .send();
    }
}
