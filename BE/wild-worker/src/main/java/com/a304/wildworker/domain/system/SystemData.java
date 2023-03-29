package com.a304.wildworker.domain.system;

import com.a304.wildworker.common.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class SystemData {

    // 타임라인 시간
    private LocalDateTime prevBaseTime; //현재 타임라인의 시작시간
    private LocalDateTime nextBaseTime; //다음 타임라인의 시작시간

    // 자동 채굴 유저 리스트
    private HashSet<Long>[] autoMiningUserSetArr = new HashSet[Constants.STATION_COUNT + 1];

    public SystemData() {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        prevBaseTime = LocalDateTime.of(nowDate,
                LocalTime.of(nowTime.getHour(), (nowTime.getMinute() / 10) * 10, 0));
        nextBaseTime = prevBaseTime.plusMinutes(Constants.INTERVAL);

        initAutoMiningUserSetArr();
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

    /* 자동 채굴 유저 리스트 초기화 */
    public void initAutoMiningUserSetArr() {
        for (int i = 1; i <= Constants.STATION_COUNT; i++) {
            autoMiningUserSetArr[i] = new HashSet<Long>();
        }
    }

    /* 자동 채굴한 유저 추가 (이미 채굴한 유저라면 false 반환) */
    public boolean addAutoMiningUser(Long userId, Long stationId) {
        return autoMiningUserSetArr[stationId.intValue()].add(userId);
    }
}
