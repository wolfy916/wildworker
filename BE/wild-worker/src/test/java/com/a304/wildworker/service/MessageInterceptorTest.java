package com.a304.wildworker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.a304.wildworker.config.WebSocketConfig;
import com.a304.wildworker.domain.activestation.StationPool;
import com.a304.wildworker.domain.activestation.StationPoolRepository;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.exception.StationNotFoundException;
import com.a304.wildworker.interceptor.MessageInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageType;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MessageInterceptorTest {

    StationPoolRepository stationPoolRepository;
    MessageInterceptor interceptor;
    String stationDestination =
            WebSocketConfig.BROKER_DEST_PREFIX + WebSocketConfig.WS_DEST_STATION + "/";
    @Mock
    private StationRepository stationRepository;

    @BeforeEach
    void setUp() {
        stationPoolRepository = new StationPoolRepository();
        MockitoAnnotations.openMocks(this);
        interceptor = new MessageInterceptor(
                null,
                stationPoolRepository,
                stationRepository);
    }

    @Test
    @DisplayName("dest에서 stationId 읽어올 수 있음")
    void testGetStationIdFromDestination() {
        long expected = 1L;
        String destination = stationDestination + expected;
        when(stationRepository.existsById(expected)).thenReturn(true);

        long stationId = interceptor.getStationIdFromDestination(destination);

        assertEquals(expected, stationId);
    }

    @Test
    @DisplayName("찾은 역이 존재하지 않은 역이면 exception")
    void testNotExistStation() {
        long expected = 1L;
        String destination = stationDestination + expected;
        when(stationRepository.existsById(expected)).thenReturn(false);

        assertThrows(StationNotFoundException.class,
                () -> interceptor.getStationIdFromDestination(destination));
    }

    @Test
    @DisplayName("구독 경로가 옳지 않으면 exception")
    void testWrongDestination() {
        String expected = "a";
        String destination = stationDestination + expected;

        assertThrows(StationNotFoundException.class,
                () -> interceptor.getStationIdFromDestination(destination));
    }

    @Test
    @DisplayName("구독 및 해제")
    void testSubscribe() {
        long stationId = 1;
        String destination = stationDestination + stationId;
        long userId = 1;
        when(stationRepository.existsById(stationId)).thenReturn(true);

        StationPool stationPool = stationPoolRepository.findById(stationId);

        ActiveUser activeUser = new ActiveUser(userId);
        // 구독 안하면 역에 없음
        assertFalse(activeUser.isSubscribed());

        // 구독하면 있음
        interceptor.subUnsubStation(SimpMessageType.SUBSCRIBE, destination, activeUser);
        assertTrue(activeUser.isSubscribed());

        // 구독 해제하면 다시 false
        interceptor.subUnsubStation(SimpMessageType.UNSUBSCRIBE, destination, activeUser);
        assertFalse(activeUser.isSubscribed());
    }


}
