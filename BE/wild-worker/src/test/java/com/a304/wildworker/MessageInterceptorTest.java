package com.a304.wildworker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
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

    ActiveStationRepository activeStationRepository;
    MessageInterceptor interceptor;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private SystemData systemData;
    @Mock
    private TransactionLogRepository transactionLogRepository;

    @BeforeEach
    void setUp() {
        activeStationRepository = new ActiveStationRepository(systemData, transactionLogRepository);
        MockitoAnnotations.openMocks(this);
        interceptor = new MessageInterceptor(
                null,
                activeStationRepository,
                stationRepository);
    }

    @Test
    @DisplayName("dest에서 stationId 읽어올 수 있음")
    void testGetStationIdFromDestination() {
        long expected = 1L;
        String destination = "/sub/station/" + expected;
        when(stationRepository.existsById(expected)).thenReturn(true);

        long stationId = interceptor.getStationIdFromDestination(destination);

        assertEquals(expected, stationId);
    }

    @Test
    @DisplayName("찾은 역이 존재하지 않은 역이면 exception")
    void testNotExistStation() {
        long expected = 1L;
        String destination = "/sub/station/" + expected;
        when(stationRepository.existsById(expected)).thenReturn(false);

        assertThrows(StationNotFoundException.class,
                () -> interceptor.getStationIdFromDestination(destination));
    }

    @Test
    @DisplayName("구독 경로가 잘못됐으면 exception")
    void testWrongDestination() {
        String expected = "asdf";
        String destination = "/sub/station/" + expected;

        assertThrows(StationNotFoundException.class,
                () -> interceptor.getStationIdFromDestination(destination));
    }

    @Test
    @DisplayName("구독/구독 해제")
    void testSubscribe() {
        long stationId = 1;
        String destination = "/sub/station/" + stationId;
        long userId = 1;
        when(stationRepository.existsById(stationId)).thenReturn(true);

        ActiveStation activeStation = activeStationRepository.findById(stationId);

        // 구독 안하면 역에 없음
        assertFalse(activeStation.getSubscribers().containsKey(userId));

        // 구독하면 있음
        interceptor.subUnsubStation(SimpMessageType.SUBSCRIBE, destination, userId);
        assertTrue(activeStation.getSubscribers().containsKey(userId));

        // 구독 해제하면 다시 false
        interceptor.subUnsubStation(SimpMessageType.UNSUBSCRIBE, destination, userId);
        assertFalse(activeStation.getSubscribers().containsKey(userId));
    }


}
