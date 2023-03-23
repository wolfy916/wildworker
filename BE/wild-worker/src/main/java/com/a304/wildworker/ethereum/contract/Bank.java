package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.domain.user.User;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

@Slf4j
@RequiredArgsConstructor
@Component
public class Bank {

    private final Won won;
    private static final long AMOUNT_MANUAL_MINE = 100L;

    public void manualMine(User user) throws CipherException, IOException {
        String to = getUserWalletAddress(user, user.getWalletPassword());
        won.manualMine(to, AMOUNT_MANUAL_MINE);
    }

    private String getUserWalletAddress(User user, String walletPassword)
            throws IOException, CipherException {
        return WalletUtils.loadCredentials(walletPassword,
                        WalletUtils.getDefaultKeyDirectory() + File.separator + user.getWallet())
                .getAddress();
    }
}
