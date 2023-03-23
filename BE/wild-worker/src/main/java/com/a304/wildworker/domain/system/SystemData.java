package com.a304.wildworker.domain.system;

import com.a304.wildworker.common.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class SystemData {

    private LocalDateTime prevBaseTime; //현재 타임라인의 시작시간
    private LocalDateTime nextBaseTime; //다음 타임라인의 시작시간

    public SystemData() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        prevBaseTime = LocalDateTime.of(nowDate,
                LocalTime.of(nowTime.getHour(), (nowTime.getMinute() / 10) * 10, 0));
        nextBaseTime = prevBaseTime.plusMinutes(Constants.INTERVAL);
    }

    public String getPrevBaseTime() {
        return prevBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getNextBaseTime() {
        return nextBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /* 시간 갱신 */
    public void updateBaseTime() {
        prevBaseTime = nextBaseTime;
        nextBaseTime = prevBaseTime.plusMinutes(Constants.INTERVAL);
    }
}
