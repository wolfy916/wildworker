package com.a304.wildworker.ethereum.service;

import com.a304.wildworker.ethereum.exception.WalletCreationException;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.WalletUtils;

/**
 * 블록체인 지갑을 생성하는 객체
 */
@Slf4j
public class WalletProvider {

    /**
     * @param walletPassword wallet을 만들기 위한 값
     * @return walletFileName wallet file의 이름
     * @throws WalletCreationException 지갑 생성 시 에러 발생
     */
    public static String createUserWallet(
            String walletPassword) throws WalletCreationException {
        File defaultDirectory = new File(WalletUtils.getDefaultKeyDirectory());
        if (!defaultDirectory.exists()) {
            defaultDirectory.mkdirs();
        }

        String walletFile;
        try {
            walletFile = WalletUtils.generateLightNewWalletFile(walletPassword,
                    defaultDirectory);
        } catch (Exception e) {
            log.error("error on create wallet : {}", e.getMessage());
            throw new WalletCreationException();
        }

        return walletFile;
    }
}
