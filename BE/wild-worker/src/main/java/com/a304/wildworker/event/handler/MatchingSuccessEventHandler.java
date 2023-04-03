package com.a304.wildworker.event.handler;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.config.WebSocketConfig;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.common.MatchProgress;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.MatchingResponse;
import com.a304.wildworker.dto.response.common.MiniGameType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.common.EventPublish;
import com.a304.wildworker.exception.UserNotFoundException;
import com.a304.wildworker.service.ScheduleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MatchingSuccessEventHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final ScheduleService scheduleService;

    @EventListener
    public void insertMatchRepository(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - insert to MatchRepo: {}", event);
        matchRepository.save(event.getMatch());
    }

    @EventListener
    public void changeUserStatus(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - change User Status: {}", event);
        Match match = event.getMatch();
        for (ActiveUser activeUser :
                match.getUsers()) {
            activeUser.setMatchable(false);
        }
    }

    @EventListener
    public void sendMessage(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - send message: {}", event);
        Match match = event.getMatch();
        List<ActiveUser> activeUsers = match.getUsers();
        List<User> users = activeUsers.stream()
                .map(a -> userRepository.findById(a.getUserId())
                        .orElseThrow(UserNotFoundException::new))
                .collect(Collectors.toList());

        match.changeProgress(MatchProgress.SELECTING);

        for (int i = 0; i < users.size(); i++) {
            User enemy = getEnemy(i, users);
            MatchingResponse matchingResponse = MatchingResponse.of(match, enemy);
            WSBaseResponse<MatchingResponse> response = WSBaseResponse.miniGame(
                    MiniGameType.MATCHING).data(matchingResponse);
            String sessionId = activeUsers.get(i).getWebsocketSessionId();
            messagingTemplate.convertAndSendToUser(sessionId,
                    WebSocketConfig.BROKER_DEST_PREFIX_USER,
                    response,
                    WebSocketUtils.createHeaders(sessionId));
        }

        scheduleService.scheduleWithDelay(endReceiveCoolTime(match),
                match.getTimeLimitSec() + Constants.SELECTING_DELAY_TIME);
    }

    @EventPublish
    public Runnable endReceiveCoolTime(Match match) {
        return match::endSelected;
    }


    private User getEnemy(int idx, List<User> users) {
        return users.get((idx + 1) % users.size());
    }
}
