package com.a304.wildworker.domain.system;

import com.a304.wildworker.common.Constants;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SystemData {

    // 타임라인 시간
    private LocalDateTime prevBaseTime; // 이전 타임라인의 시작시간
    private LocalDateTime nowBaseTime; // 현재 타임라인의 시작시간
    private LocalDateTime nextBaseTime; // 다음 타임라인의 시작시간
    private LocalDateTime autoMiningBaseTime;   // 자동 채굴 상태 초기화된 시간
    private LocalDateTime investmentBaseTime;   // 투자금 초기화된 시간

    public SystemData() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        LocalDateTime nowDateTime = LocalDateTime.now();

        // 타임라인 시간 세팅
        nowBaseTime = LocalDateTime.of(nowDate,
                LocalTime.of(nowTime.getHour(), (nowTime.getMinute() / 10) * 10, 0));
        prevBaseTime = nowBaseTime.minusMinutes(Constants.INTERVAL);
        nextBaseTime = nowBaseTime.plusMinutes(Constants.INTERVAL);

        // 자동 채굴 초기화 시간 세팅
        LocalDate autoMineDate = (nowTime.getHour() < 3) ? nowDate.minusDays(1) : nowDate;
        int autoMineTime = ((nowTime.getHour() >= 3) && (nowTime.getHour() < 15)) ? 3 : 15;
        autoMiningBaseTime = LocalDateTime.of(autoMineDate, LocalTime.of(autoMineTime, 0, 0));

        // 투자금 초기화 시간 세팅
        investmentBaseTime = nowDateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .with(LocalTime.MIDNIGHT);
    }

    public String getPrevBaseTimeString() {
        return prevBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getNowBaseTimeString() {
        return nowBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getNextBaseTimeString() {
        return nextBaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    /* 타임라인 시간 갱신 */
    public void updateBaseTime() {
        prevBaseTime = nowBaseTime;
        nowBaseTime = nextBaseTime;
        nextBaseTime = nextBaseTime.plusMinutes(Constants.INTERVAL);
    }

    /* 자동 채굴 초기화 시간 갱신 */
    public void initAutoMiningTime() {
        autoMiningBaseTime = autoMiningBaseTime.plusHours(12);
    }

    /* 투자금 초기화 시간 갱신 */
    public void initInvestmentTime() {
        investmentBaseTime = investmentBaseTime.plusWeeks(1);
    }

}
