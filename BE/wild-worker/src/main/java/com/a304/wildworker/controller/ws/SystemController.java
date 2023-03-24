package com.a304.wildworker.controller.ws;

import com.a304.wildworker.common.WebSocketUtils;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.dto.response.StationWithUserResponse;
import com.a304.wildworker.dto.response.common.StationType;
import com.a304.wildworker.dto.response.common.WSBaseResponse;
import com.a304.wildworker.service.SystemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
@MessageMapping("/system")
public class SystemController {

    private final SystemService systemService;

    private final SimpMessagingTemplate messagingTemplate;

    /* 사용자 좌표 주기적 수신 */
    @MessageMapping("/location")
    public void getUserLocation(@Header("simpSessionId") String sessionId,
            @Header("simpUser") ActiveUser user, Location location) {
        StationWithUserResponse stationWithUserResponse
                = systemService.checkUserLocation(user, location);

        // 역 변동이 있는 경우 NOTIFICATION
        if (stationWithUserResponse != null) {
            WSBaseResponse<StationWithUserResponse> response = WSBaseResponse.station(
                    StationType.STATUS).data(stationWithUserResponse);

            messagingTemplate.convertAndSendToUser(sessionId, "/queue", response,
                    WebSocketUtils.createHeaders(sessionId));
        }
    }
}
