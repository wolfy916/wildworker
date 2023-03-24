package com.a304.wildworker.service;

import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.exception.PaperTooLowException;
import com.a304.wildworker.exception.UserNotFoundException;
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
    private static final int SELL_LIMIT = 100; // 종이 판매 단위

    public void sellPaper(Long userId) throws CipherException, IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getNumberOfCollectedPaper() < SELL_LIMIT) {
            throw new PaperTooLowException();
        }

        user.sellPaper();
        bank.manualMine(user);
    }
}
