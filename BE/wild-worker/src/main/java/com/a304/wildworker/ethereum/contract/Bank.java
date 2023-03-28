package com.a304.wildworker.ethereum.contract;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * 서버 내에서 사용될 System wallet. 스마트 컨트랙트를 호출함
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class Bank {

    private final WonContract wonContract;
    private final StationContract stationContract;

    private static final long AMOUNT_MANUAL_MINE = 100L;
    private static final long AMOUNT_AUTO_MINE = 100L;

    /**
     * 수동 채굴 메소드 요청 시 AMOUNT_MANUAL_MINE 만큼의 WON 을 얻음
     *
     * @param user 수동 채굴을 수행할 사용자
     * @throws CipherException
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> manualMine(User user)
            throws CipherException, IOException {
        String to = getUserWalletAddress(user);
        return wonContract.manualMine(to, AMOUNT_MANUAL_MINE);
    }

    /**
     * station에서 user로 돈(won) 송금
     *
     * @param station 돈을 줄 역
     * @param user    돈을 받을 사용자
     * @throws CipherException
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> autoMine(Station station, User user)
            throws CipherException, IOException {
        String from = station.getAddress();
        String to = getUserWalletAddress(user);

        return stationContract.autoMine(from, to, AMOUNT_AUTO_MINE);
    }

    /**
     * user가 station에 돈 투자
     *
     * @param station 돈(WON)을 받을 역
     * @param user    돈(WON)을 지불할 사용자
     * @throws CipherException
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> invest(Station station, User user, Long amount)
            throws CipherException, IOException {
        String stationAddress = station.getAddress();
        String userAddress = getUserWalletAddress(user);

        return stationContract.invest(stationAddress, userAddress, amount);
    }

    /**
     * 이전 기준점(10분) 부터 현재까지 모인 수수료 분배
     *
     * @param station 수수료를 분배할 역
     * @return CompletableFuture 를 반환하므로 콜백 실행 가능
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> distributeInvestReward(Station station)
            throws IOException {
        String stationAddress = station.getAddress();
        Long currentCommission = station.getCommission();
        return stationContract.distributeInvestReward(stationAddress, currentCommission);
    }

    /**
     * 역에게 도망비 입금
     *
     * @param station 도망비를 받을 역
     * @param user    도망비를 입금할 사용자
     * @param amount  도망비
     * @return CompletableFuture<TransactionReceipt> 트랜잭션 처리 결과 반환
     * @throws CipherException
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> sendRunCost(Station station, User user,
            Long amount)
            throws CipherException, IOException {
        return sendTransfer(station, user, amount);
    }

    /**
     * 역에게 게임비 입금
     *
     * @param station 게임비를 받을 역
     * @param user    게임비를 입금할 사용자
     * @param amount  게임비
     * @return CompletableFuture<TransactionReceipt> 트랜잭션 처리 결과 반환
     * @throws CipherException
     * @throws IOException
     */
    public CompletableFuture<TransactionReceipt> sendGameCost(Station station, User user,
            Long amount)
            throws CipherException, IOException {
        return sendTransfer(station, user, amount);
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

    private String getUserWalletAddress(User user) throws IOException, CipherException {
        return WalletUtils.loadCredentials(user.getWalletPassword(),
                        WalletUtils.getDefaultKeyDirectory() + File.separator + user.getWallet())
                .getAddress();
    }

    private CompletableFuture<TransactionReceipt> sendTransfer(
            Station station, User user, Long amount) throws IOException, CipherException {
        String stationAddress = station.getAddress();
        String userAddress = getUserWalletAddress(user);
        return wonContract.transferWon(userAddress, stationAddress, amount);
    }
}
