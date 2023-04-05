package com.a304.wildworker.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.a304.wildworker.config.WebSocketConfig;
import com.a304.wildworker.domain.activestation.ActiveStationRepository;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.system.SystemData;
import com.a304.wildworker.domain.transaction.TransactionLogRepository;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.event.SetMatchCoolTimeEvent;
import com.a304.wildworker.service.EventService;
import com.a304.wildworker.service.interceptor.MessageInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
@RunWith(MockitoJUnitRunner.class)
public class ActiveUserTest {

    @Autowired
    ApplicationEvents events;
    ActiveStationRepository activeStationRepository;
    ActiveUserRepository activeUserRepository;
    @Mock
    UserRepository userRepository;
    @Autowired
    EventService userService;

    MessageInterceptor interceptor;
    String stationDestination =
            WebSocketConfig.BROKER_DEST_PREFIX + WebSocketConfig.WS_DEST_STATION;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private SystemData systemData;
    @Mock
    private TransactionLogRepository transactionLogRepository;

    @BeforeEach
    void setUp() {
        activeUserRepository = new ActiveUserRepository();
        activeStationRepository = new ActiveStationRepository(systemData, transactionLogRepository);
        MockitoAnnotations.openMocks(this);
        interceptor = new MessageInterceptor(
                activeUserRepository,
                stationRepository,
                userService);
    }

    @Test
    public void testActiveUser() {
        Long stationId = 1L;
        String destination = stationDestination + "/" + stationId;
        ActiveUser activeUser = new ActiveUser(1L);
        activeUserRepository.save(activeUser);
        when(stationRepository.existsById(stationId)).thenReturn(true);

        activeUser.setStationId(stationId);
        activeUser.setMatchable(true);
        interceptor.subUnsubStation(SimpMessageType.SUBSCRIBE, destination, activeUser);
        assertTrue(activeUser.isSubscribed() && activeUser.isMatchable()
                && activeUser.getCoolTime() != null);

        int count = (int) events.stream(SetMatchCoolTimeEvent.class).count();
        assertEquals(1, count);
        assertNotNull(activeUser.getCoolTime());

//        while (!activeUser.getCoolTime().isDone()) {
//
//        }
//        StationPool stationPool = stationPoolRepository.findById(stationId);
//        assertEquals(1, stationPool.getPool().size());
    }
}
