package com.a304.wildworker.service;

import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MiningService {

    private final Bank bank;
    private final UserRepository userRepository;
    private static final int SELL_UNIT = 100; // 종이 판매 단위

    public void sellPaper(Long userId) throws CipherException, IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(
                        "존재하지 않는 사용자입니다."));// TODO: 2023-03-24 exception 정의 필요

        if (user.getNumberOfCollectedPaper() < SELL_UNIT) {
            throw new RuntimeException("서류가 너무 적습니다."); // TODO: 2023-03-24 exception 정의 필요
        }
        
        user.sellPaper();
        bank.manualMine(user);
    }
}
