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

    public void sellPaper(Long userId) throws CipherException, IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(RuntimeException::new);// TODO: 2023-03-24 exception 정의 필요
        if (user.getNumberOfCollectedPaper() < 100) {
            throw new RuntimeException(); // TODO: 2023-03-24 exception 정의 필요
        }
        user.sellPaper();
        bank.manualMine(user);
    }
}
