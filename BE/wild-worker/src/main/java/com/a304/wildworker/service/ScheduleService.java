package com.a304.wildworker.service;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final TaskScheduler scheduler;

    public ScheduledFuture<?> scheduleWithDelay(Runnable runnable, int delaySec) {
        int coolTime = delaySec * 1000;
        Date startTime = new Date(System.currentTimeMillis() + coolTime);
        log.debug("schedule: {} {}", coolTime, startTime);
        return scheduler.schedule(runnable, startTime);
    }
}
