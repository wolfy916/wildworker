package com.a304.wildworker.service;

import com.a304.wildworker.domain.common.TitleCode;
import com.a304.wildworker.domain.minigame.MiniGameLogRepository;
import com.a304.wildworker.domain.title.Title;
import com.a304.wildworker.domain.title.TitleAwardedRepository;
import com.a304.wildworker.domain.title.TitleRepository;
import com.a304.wildworker.domain.title.strategy.LooserStrategy;
import com.a304.wildworker.domain.title.strategy.PoorStrategy;
import com.a304.wildworker.domain.title.strategy.RichStrategy;
import com.a304.wildworker.domain.title.strategy.RiskTakerStrategy;
import com.a304.wildworker.domain.title.strategy.RunnerStrategy;
import com.a304.wildworker.domain.title.strategy.TitleStrategy;
import com.a304.wildworker.domain.title.strategy.WinnerStrategy;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.TitleDto;
import com.a304.wildworker.event.GetTitleEvent;
import com.a304.wildworker.exception.NoSuchCodeException;
import com.a304.wildworker.exception.UserNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TitleService {

    private final UserRepository userRepository;
    private final TitleRepository titleRepository;
    private final TitleAwardedRepository titleAwardedRepository;
    private final MiniGameLogRepository miniGameLogRepository;
    private final ApplicationEventPublisher publisher;

    private Map<Long, TitleStrategy> titleStrategyMap;

    public TitleService(UserRepository userRepository, TitleRepository titleRepository,
            TitleAwardedRepository titleAwardedRepository,
            MiniGameLogRepository miniGameLogRepository,
            ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.titleRepository = titleRepository;
        this.titleAwardedRepository = titleAwardedRepository;
        this.miniGameLogRepository = miniGameLogRepository;
        this.publisher = publisher;

        titleStrategyMap = new ConcurrentHashMap<>();

        // 승부사
        titleStrategyMap.put(TitleCode.RISK_TAKER.getId(),
                new RiskTakerStrategy(userRepository, titleRepository, miniGameLogRepository));
        // 쫄보
        titleStrategyMap.put(TitleCode.RUNNER.getId(),
                new RunnerStrategy(userRepository, titleRepository, miniGameLogRepository));
        // 무일푼
        titleStrategyMap.put(TitleCode.POOR.getId(),
                new PoorStrategy(userRepository, titleRepository, miniGameLogRepository));
        // 부자
        titleStrategyMap.put(TitleCode.RICH.getId(),
                new RichStrategy(userRepository, titleRepository, miniGameLogRepository));
        // 똥손
        titleStrategyMap.put(TitleCode.LOOSER.getId(),
                new LooserStrategy(userRepository, titleRepository, miniGameLogRepository));
        // 금손
        titleStrategyMap.put(TitleCode.WINNER.getId(),
                new WinnerStrategy(userRepository, titleRepository, miniGameLogRepository));
    }

    /* 칭호 조건 달성 시 획득 처리 */
    public void checkAndGetTitle(Long userId, TitleCode titleCode) {
        // 이미 획득한 경우
        if (alreadyGetTitle(userId, titleCode.getId())) {
            return;
        }

        TitleStrategy titleStrategy = titleStrategyMap.get(titleCode.getId());

        if (titleStrategy == null) {
            throw new NoSuchCodeException(titleCode);
        } else {
            // 획득 조건 달성한 경우
            if (titleStrategy.checkTitle(userId)) {
                getTitle(userId, titleCode);
            }
        }

    }

    /* 칭호 보유 여부 확인 */
    public boolean alreadyGetTitle(Long userId, Long titleId) {
        if (titleAwardedRepository.existsByTitleIdAndUserId(titleId, userId)) {
            return true;
        }
        return false;
    }

    /* 새 칭호 획득 */
    public void getTitle(Long userId, TitleCode titleCode) {
        User user = getUserOrElseThrow(userId);
        Title title = getTitleOrElseThrow(titleCode);

        // 칭호 획득 이벤트 발생
        publisher.publishEvent(new GetTitleEvent(user, title));
    }

    public List<TitleDto> getTitleList(Long userId) {
        List<TitleDto> titleList = new ArrayList<>();
        titleAwardedRepository.findByUserId(userId).stream().forEach(titleAwarded -> {
            titleList.add(TitleDto.of(titleAwarded.getTitle()));
        });

        return titleList;
    }

    public User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public Title getTitleOrElseThrow(TitleCode titleCode) {
        return titleRepository.findById(titleCode.getId())
                .orElseThrow(() -> new NoSuchCodeException(titleCode));
    }
}
