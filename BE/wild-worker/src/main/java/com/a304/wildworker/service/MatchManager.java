package com.a304.wildworker.service;


import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MatchManager {

    private final static int COOL_TIME_MIN = 10;
    private final static int COOL_TIME_BOUND = 30 - COOL_TIME_MIN;
    private final TaskScheduler taskScheduler;
    private final Random random;
    ActiveStationRepository activeStationRepository;

    public ScheduledFuture<?> scheduleCoolTime() {
        int coolTime = getRandomCoolTime() * 1000;
        Date startTime = new Date(System.currentTimeMillis() + coolTime);
        return taskScheduler.schedule(this::event, startTime);
    }

    private int getRandomCoolTime() {
        return random.nextInt(COOL_TIME_BOUND) + COOL_TIME_MIN;
    }

    private void event() {
        log.info("event occurred!");
    }

}
