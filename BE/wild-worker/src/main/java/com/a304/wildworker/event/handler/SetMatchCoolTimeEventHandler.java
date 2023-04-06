package com.a304.wildworker.event.handler;

import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.event.SetMatchCoolTimeEvent;
import com.a304.wildworker.service.ActiveStationService;
import com.a304.wildworker.service.ScheduleService;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetMatchCoolTimeEventHandler {

    private final static int COOL_TIME_MIN = 10;
    private final static int COOL_TIME_BOUND = 30 - COOL_TIME_MIN;
    private final Random random = new Random();
    private final ActiveStationService activeStationService;
    private final ScheduleService scheduleService;

    @EventListener
    public void scheduleCoolTime(SetMatchCoolTimeEvent event) {
        ActiveUser activeUser = event.getUser();
        int coolTime = getRandomCoolTime();
        log.debug("event occur: SetCoolTime - scheduleCoolTime - user {}, coolTime {}",
                activeUser.getUserId(), coolTime);
        ScheduledFuture<?> scheduledFuture = scheduleService.scheduleWithDelay(
                scheduledTaskForCoolTimeEnd(activeUser, activeUser.getStationId()),
                coolTime);
        activeUser.setCoolTime(scheduledFuture);
    }

    private int getRandomCoolTime() {
        return random.nextInt(COOL_TIME_BOUND) + COOL_TIME_MIN;
    }

    public Runnable scheduledTaskForCoolTimeEnd(ActiveUser activeUser, Long stationId) {
        return () -> {
            log.info("event: coolTimeEnd: user {}, station {}", activeUser.getUserId(), stationId);
            //동일한 역 && 매칭 가능한 상태 -> pool에 추가
            if (stationId.equals(activeUser.getStationId()) && activeUser.canMatching()) {
                activeStationService.insertToStationPool(activeUser, stationId);
            }
        };
    }
}
