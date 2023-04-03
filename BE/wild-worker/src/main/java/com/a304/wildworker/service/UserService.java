package com.a304.wildworker.service;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.UserResponse;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUser(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get()
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.of(user);
    }

    public long getUserId(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get()
                .orElseThrow(UserNotFoundException::new);
        return user.getId();
    }

    public List<User> activeUserToUser(List<ActiveUser> activeUsers) {
        return activeUsers.stream()
                .map(a -> userRepository.findById(a.getUserId())
                        .orElseThrow(UserNotFoundException::new))
                .collect(Collectors.toList());
    }
}
