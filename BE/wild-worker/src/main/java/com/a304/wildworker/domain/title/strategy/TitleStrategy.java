package com.a304.wildworker.domain.title.strategy;

import com.a304.wildworker.domain.minigame.MiniGameLog;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;

@AllArgsConstructor
public abstract class TitleStrategy {

    final UserRepository userRepository;
    final TitleRepository titleRepository;
    final MiniGameLogRepository miniGameLogRepository;

    abstract public boolean checkTitle(Long userId);

    /* count 만큼 최근 게임로그 반환 */
    public List<MiniGameLog> getGameLogByCount(Long userId, int count) {
        PageRequest pageRequest = PageRequest.of(0, count);
        return miniGameLogRepository.findByUser1IdOrUser2IdOrderByCreatedAtDesc(userId, userId,
                pageRequest).getContent();
    }

    public User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

}
