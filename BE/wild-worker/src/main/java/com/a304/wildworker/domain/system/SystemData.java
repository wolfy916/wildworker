package com.a304.wildworker.domain.system;

import com.a304.wildworker.common.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class SystemData {

    // 타임라인 시간
    private LocalDateTime prevBaseTime; // 현재 타임라인의 시작시간
    private LocalDateTime nextBaseTime; // 다음 타임라인의 시작시간
    private LocalDateTime autoMiningBaseTime;   // 자동 채굴 상태 초기화된 시간

    public SystemData() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        // 타임라인 시간 세팅
        prevBaseTime = LocalDateTime.of(nowDate,
                LocalTime.of(nowTime.getHour(), (nowTime.getMinute() / 10) * 10, 0));
        nextBaseTime = prevBaseTime.plusMinutes(Constants.INTERVAL);

        // 자동 채굴 초기화 시간 세팅
        LocalDate autoMineDate = (nowTime.getHour() < 3) ? nowDate.minusDays(1) : nowDate;
        int autoMineTime = ((nowTime.getHour() >= 3) && (nowTime.getHour() < 15)) ? 3 : 15;
        autoMiningBaseTime = LocalDateTime.of(autoMineDate, LocalTime.of(autoMineTime, 0, 0));
    }

    public String getPrevBaseTime() {
        return prevBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getNextBaseTime() {
        return nextBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public LocalDateTime getAutoMiningBaseTime() {
        return autoMiningBaseTime;
    }

    /* 타임라인 시간 갱신 */
    public void updateBaseTime() {
        prevBaseTime = nextBaseTime;
        nextBaseTime = prevBaseTime.plusMinutes(Constants.INTERVAL);
    }

    /* 자동 채굴 초기화 시간 갱신 */
    public void initAutoMiningTime() {
        autoMiningBaseTime = autoMiningBaseTime.plusHours(12);
    }

}
