package com.a304.wildworker.ethereum.service;

import java.io.File;
import org.web3j.crypto.WalletUtils;

/**
 * 사용자의 블록체인 지갑을 생성하는 객체
 */
public class WalletProvider {

    /**
     * @param walletPassword wallet을 만들기 위한 값
     * @return walletFilePath
     * @throws Exception
     */
    public static String createUserWallet(
            String walletPassword) // walletPassword를 통해 내부적으로 privateKey를 생성해서 사용
            throws Exception {
        File defaultDirectory = new File(WalletUtils.getDefaultKeyDirectory());
        if (!defaultDirectory.exists()) {
            defaultDirectory.mkdirs();
        }
        return WalletUtils.generateLightNewWalletFile(walletPassword,
                defaultDirectory);
    }
}
