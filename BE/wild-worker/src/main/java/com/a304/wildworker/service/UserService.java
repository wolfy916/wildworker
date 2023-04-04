package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.TitleCode;
import com.a304.wildworker.domain.common.TitleShowType;
import com.a304.wildworker.domain.dominator.DominatorLog;
import com.a304.wildworker.domain.dominator.DominatorLogRepository;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.title.Title;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.request.ChangeUserInfoRequest;
import com.a304.wildworker.dto.response.TitleDto;
import com.a304.wildworker.dto.response.TitleListResponse;
import com.a304.wildworker.dto.response.UserResponse;
import com.a304.wildworker.exception.DuplicatedNameException;
import com.a304.wildworker.exception.NotOwnTitleException;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.exception.TitleNotFoundException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DominatorLogRepository dominatorLogRepository;
    private final TitleRepository titleRepository;
    private final StationRepository stationRepository;
    private final SystemData systemData;
    private final TitleService titleService;

    public UserResponse getUser(String email) {
        User user = Optional.of(userRepository.findByEmail(email)).get()
                .orElseThrow(UserNotFoundException::new);
        TitleDto titleDto = null;

        // 지배자 칭호인 경우
        if (user.getTitleShowType() == TitleShowType.DOMINATOR) {
            if (user.getTitleId().equals(TitleCode.NONE.getId())) {
                titleDto = TitleDto.of(getTitleOrElseThrow(user.getTitleId()));
            } else {
                titleDto = TitleDto.of(getStationOrElseThrow(user.getTitleId()));
            }
        }
        // 일반 칭호인 경우
        else if (user.getTitleShowType() == TitleShowType.TITLE) {
            titleDto = TitleDto.of(getTitleOrElseThrow(user.getTitleId()));
        }

        return UserResponse.of(user, titleDto);
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

            // 지배자로 설정한 경우
            if (request.getTitleType() == TitleShowType.DOMINATOR.ordinal()) {
                Station mainStation = getMostExpensiveStation(userId);

                // 지배하고 있는 가장 비싼 역으로 설정
                if (mainStation != null) {
                    user.setTitleId(mainStation.getId());
                } else {
                    user.setTitleId(TitleCode.NONE.getId());
                }
            }
        }

        // 대표 칭호 고유번호 변경
        if (request.getMainTitleId() != null) {
            // 보유 여부 확인
            if (request.getMainTitleId() == -1 ||
                    titleService.alreadyGetTitle(userId, request.getMainTitleId())) {
                user.setTitleId(request.getMainTitleId());
            } else {
                throw new NotOwnTitleException();
            }
        }

        // 캐릭터 종류 변경
        if (request.getCharacterType() != null) {
            user.setCharacterId(CharacterType.fromOrdinary(request.getCharacterType()));
        }
    }

    /* 보유 칭호목록 조회 */
    public TitleListResponse getTitleList(Long userId) {
        User user = getUserOrElseThrow(userId);

        return TitleListResponse.builder()
                .titleType(user.getTitleShowType().ordinal())
                .mainTitleId(user.getTitleId())
                .titles(titleService.getTitleList(userId))
                .build();
    }

    /* 유저가 지배하는 역 중 가장 비싼 역 반환 */
    private Station getMostExpensiveStation(Long userId) {
        List<DominatorLog> dominatorLogList = dominatorLogRepository.findByUserIdAndDominateStartTime(
                userId, systemData.getNowBaseTimeString());

        Station mostExpensiveStation = null;

        // 가장 비싼 역 찾기
        for (DominatorLog log : dominatorLogList) {
            Station nowStation = log.getStation();

            if (mostExpensiveStation == null
                    || mostExpensiveStation.getBalance() < nowStation.getBalance()) {
                mostExpensiveStation = nowStation;
            }
        }

        return mostExpensiveStation;
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    private Station getStationOrElseThrow(Long stationId) {
        return stationRepository.findById(stationId)
                .orElseThrow(StationNotFoundException::new);
    }

    public Title getTitleOrElseThrow(Long titleId) {
        return titleRepository.findById(titleId)
                .orElseThrow(TitleNotFoundException::new);
    }
}
