package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.event.SetCoolTimeEvent;
import com.a304.wildworker.service.EventService;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetCoolTimeEventHandler {

    private final static int COOL_TIME_MIN = 10;
    private final static int COOL_TIME_BOUND = 30 - COOL_TIME_MIN;
    private final TaskScheduler taskScheduler;
    private final Random random = new Random();
    private final ActiveStationRepository activeStationRepository;
    private final ActiveUserRepository activeUserRepository;
    private final EventService eventService;

    @EventListener
    public void scheduleCoolTime(SetCoolTimeEvent event) {
        ActiveUser activeUser = event.getUser();
        log.info("event occur: SetCoolTime - scheduleCoolTime: {}", activeUser.getUserId());
        int coolTime = getRandomCoolTime() * 1000;
        Date startTime = new Date(System.currentTimeMillis() + coolTime);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(scheduledTaskForCoolTimeEnd(
                activeUser.getUserId(), activeUser.getStationId()), startTime);
        activeUser.setCoolTime(scheduledFuture);
    }

    private int getRandomCoolTime() {
        return random.nextInt(COOL_TIME_BOUND) + COOL_TIME_MIN;
    }

    public Runnable scheduledTaskForCoolTimeEnd(Long userId, Long stationId) {
        return () -> {
            log.info("event occur: coolTimeEnd!: {}", userId);
            ActiveUser activeUser = activeUserRepository.findById(userId).orElseThrow();
            activeUser.setCoolTime(null);   //쿨타임 초기화

            //현재 역이 달라졌으면 return
            if (!Objects.equals(stationId, activeUser.getStationId())) {
                return;
            }

            //구독 중이고 매칭 가능한 상태면, 풀에 추가
            if (activeUser.isSubscribed() && activeUser.isMatchable()) {
                ActiveStation activeStation = activeStationRepository.findById(stationId);
                eventService.insertToStationPool(userId, activeStation);
            }
        };
    }
}
