package com.a304.wildworker.ethereum.service;

import com.a304.wildworker.ethereum.exception.InvalidAddressException;
import java.io.File;
import java.io.IOException;
import org.springframework.stereotype.Service;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;

@Service
public class WalletService {

    private final Web3j web3j;
    private final RootWallet rootWallet;

    public WalletService(Web3j web3j, RootWallet rootWallet) {
        this.web3j = web3j;
        this.rootWallet = rootWallet;
    }

    public String createWorkerWallet(
            String walletPassword) // walletPassword를 통해 내부적으로 privateKey를 생성해서 사용
            throws Exception {
        File defaultDirectory = new File(WalletUtils.getDefaultKeyDirectory());
        String walletFile = WalletUtils.generateLightNewWalletFile(walletPassword,
                defaultDirectory);

        String address = WalletUtils.loadCredentials(walletPassword, walletFile)
                .getAddress();

        rootWallet.sendGasTo(address); // 초기 가스비 입금

        return walletFile;
    }

    // TODO: 2023-03-22 won을 읽어와야 함
    public EthGetBalance getEthBalance(String address) throws IOException {
        if (!WalletUtils.isValidAddress(address)) {
            throw new InvalidAddressException();
        }
        return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .send();
    }
}
