package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.common.MatchStatus;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.dto.response.MatchCancelResponse;
import com.a304.wildworker.dto.response.MatchingResponse;
import com.a304.wildworker.dto.response.MatchingResponse.UserDto;
import com.a304.wildworker.dto.response.MiniGameResultResponse;
import com.a304.wildworker.dto.response.MiniGameStartResponse;
import com.a304.wildworker.dto.response.TitleDto;
import com.a304.wildworker.dto.response.common.MiniGameType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.event.MatchCancelEvent;
import com.a304.wildworker.event.MatchingSuccessEvent;
import com.a304.wildworker.event.MiniGameEndEvent;
import com.a304.wildworker.event.MiniGameStartEvent;
import com.a304.wildworker.service.ActiveUserService;
import com.a304.wildworker.service.MessageService;
import com.a304.wildworker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMiniGameMessageHandler {

    private final ActiveUserService activeUserService;
    private final MessageService messageService;
    private final UserService userService;

    /**
     * 게임 매칭 메시지
     *
     * @param event MatchingSuccessEvent
     */
    @Order(3)
    @EventListener
    public void sendMatching(MatchingSuccessEvent event) {
        log.info("event occur: MatchingSuccessEvent - send message: {}", event);
        Match match = event.getMatch();

        match.getUsers().forEach(user -> {
            String sessionId = activeUserService.getSessionIdById(user.getId());
            if (sessionId != null) {
                User enemy = match.getEnemy(user);
                TitleDto enemyTitle = userService.getTitleDto(enemy);
                UserDto enemyDto = UserDto.of(enemy, enemyTitle);
                MatchingResponse data = MatchingResponse.of(match, enemyDto);
                WSBaseResponse<MatchingResponse> response = WSBaseResponse.miniGame(
                        MiniGameType.MATCHING).data(data);
                messageService.sendToUser(sessionId, response);
            }
        });
        match.changeStatus(MatchStatus.SELECTING_START);
    }

    /**
     * 게임 취소 메시지
     *
     * @param event MatchCancelEvent
     */
    @EventListener
    public void sendCancel(MatchCancelEvent event) {
        log.info("event occur: MatchCancelEvent - send message: {}", event);
        Match match = event.getMatch();
        match.getUsers().forEach(user -> {
            String sessionId = activeUserService.getSessionIdById(user.getId());
            if (sessionId != null) {
                MatchCancelResponse data = MatchCancelResponse.of(match.isRunner(user.getId()));
                WSBaseResponse<MatchCancelResponse> response =
                        WSBaseResponse
                                .miniGame(MiniGameType.CANCEL)
                                .data(data);
                messageService.sendToUser(sessionId, response);
            }
        });
    }

    /**
     * 게임 시작 메시지
     *
     * @param event MiniGameStartEvent
     */
    @EventListener
    public void sendStart(MiniGameStartEvent event) {
        log.info("event occur: MiniGameStartEvent - send message: {}", event);
        Match match = event.getMatch();
        match.getUsers().forEach(user -> {
            long userId = user.getId();
            String sessionId = activeUserService.getSessionIdById(userId);
            if (sessionId != null) {
                MiniGameStartResponse data = MiniGameStartResponse.of(
                        match.getMiniGame().getCode());
                WSBaseResponse<MiniGameStartResponse> response =
                        WSBaseResponse.miniGame(MiniGameType.START).data(data);
                messageService.sendToUser(sessionId, response);
            }
        });
    }

    /**
     * 게임 결과 메세지
     *
     * @param event MiniGameEndEvent
     */
    @EventListener
    public void sendResult(MiniGameEndEvent event) {
        log.info("event occur: MiniGameEndEvent - send message: {}", event);
        Match match = event.getMatch();
        match.getUsers().forEach(user -> {
            String sessionId = activeUserService.getSessionIdById(user.getId());
            if (sessionId != null) {
                User enemy = match.getEnemy(user);
                TitleDto enemyTitle = userService.getTitleDto(enemy);
                UserDto enemyDto = UserDto.of(enemy, enemyTitle);
                MiniGameResultResponse data = MiniGameResultResponse.of(match, user, enemyDto);
                WSBaseResponse<MiniGameResultResponse> response = WSBaseResponse.miniGame(
                        MiniGameType.RESULT).data(data);
                messageService.sendToUser(sessionId, response);
            }
        });
    }

}
