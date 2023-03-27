package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

/**
 * 서버 내에서 사용될 System wallet. 스마트 컨트랙트를 호출함
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class Bank {

    private final WonContract wonContract;
    private final StationContract stationContract;

    /**
     * 수동 채굴 메소드 요청 시 AMOUNT_MANUAL_MINE 만큼의 WON 을 얻음
     *
     * @param user 수동 채굴을 수행할 사용자
     * @throws CipherException
     * @throws IOException
     */
    public void manualMine(User user) throws CipherException, IOException {
        String to = getUserWalletAddress(user);
        wonContract.manualMine(to, Constants.AMOUNT_MANUAL_MINE);
    }

    /**
     * station에서 user로 돈(won) 송금
     *
     * @param station 돈을 줄 역
     * @param user    돈을 받을 사용자
     * @throws CipherException
     * @throws IOException
     */
    public void autoMine(Station station, User user) throws CipherException, IOException {
        String from = station.getAddress();
        String to = getUserWalletAddress(user);

        stationContract.autoMine(from, to, Constants.AMOUNT_AUTO_MINE);
    }

    /**
     * 현재 잔액(WON)을 확인하는 메소드
     *
     * @param user 현재 잔액을 확인할 사용자
     * @return Long 현재 잔액
     * @throws CipherException
     * @throws IOException
     */
    public Long balanceOf(User user) throws CipherException, IOException {
        String userWalletAddress = getUserWalletAddress(user);
        return wonContract.balanceOf(userWalletAddress).longValue();
    }

    private String getUserWalletAddress(User user)
            throws IOException, CipherException {
        return WalletUtils.loadCredentials(user.getWalletPassword(),
                        WalletUtils.getDefaultKeyDirectory() + File.separator + user.getWallet())
                .getAddress();
    }
}
