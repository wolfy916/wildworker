package com.a304.wildworker.service;

import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserTransactionService {

    private final UserRepository userRepository;

    public void changeBalance(long userId, long value) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.changeBalance(value);
    }

}
