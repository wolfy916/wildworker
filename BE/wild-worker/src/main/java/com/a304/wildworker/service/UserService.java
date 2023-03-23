package com.a304.wildworker.service;

import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.UserResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get().orElseThrow();
        return UserResponse.of(user);
    }

    public long getUserId(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get().orElseThrow();
        return user.getId();
    }
}
