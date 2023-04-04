package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.TitleShowType;
import com.a304.wildworker.domain.dominator.DominatorLogRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.title.Title;
import com.a304.wildworker.domain.title.TitleAwardedRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.request.ChangeUserInfoRequest;
import com.a304.wildworker.dto.response.UserResponse;
import com.a304.wildworker.exception.DuplicatedNameException;
import com.a304.wildworker.exception.NotOwnTitleException;
import com.a304.wildworker.exception.TitleNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TitleAwardedRepository titleAwardedRepository;
    private final TitleRepository titleRepository;
    private final DominatorLogRepository dominatorLogRepository;
    private final SystemData systemData;

    public UserResponse getUser(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get()
                .orElseThrow(UserNotFoundException::new);
        return UserResponse.of(user);
    }

    /* 회원정보 수정 */
    @Transactional
    public void changeUserInfo(Long userId, ChangeUserInfoRequest request) {
        User user = getUserOrElseThrow(userId);

        // 닉네임 변경
        if (request.getName() != null) {
            // 중복 확인
            if (!userRepository.existsByNameAndIdNot(request.getName(), userId)) {
                user.setName(request.getName());
            } else {
                throw new DuplicatedNameException();
            }
        }

        // 칭호 종류 변경
        if (request.getTitleType() != null) {
            user.setTitleShowType(TitleShowType.fromOrdinary(request.getTitleType()));
        }

        // 대표 칭호 고유번호 변경
        if (request.getMainTitleId() != null) {
            Title title = getTitleOrElseThrow(request.getMainTitleId());
            // 보유 여부 확인
            if (titleAwardedRepository.existsByTitleIdAndUserId(title.getId(), userId)) {
                user.setTitle(title);
            } else {
                throw new NotOwnTitleException();
            }
        }

        // 캐릭터 종류 변경
        if (request.getCharacterType() != null) {
            user.setCharacterId(CharacterType.fromOrdinary(request.getCharacterType()));
        }
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Title getTitleOrElseThrow(Long titleId) {
        return titleRepository.findById(titleId)
                .orElseThrow(TitleNotFoundException::new);
    }
}
