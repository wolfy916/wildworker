package com.a304.wildworker.controller.rest;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.sessionuser.SessionUser;
import com.a304.wildworker.dto.request.InvestmentRequest;
import com.a304.wildworker.exception.NotLoginException;
import com.a304.wildworker.service.InvestService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/investment")
@RequiredArgsConstructor
public class InvestmentController {

    private final InvestService investService;

    /* 역 투자 */
    @PostMapping("/{station-id}")
    public ResponseEntity<?> investToStation(HttpServletRequest request,
            @PathVariable("station-id") Long stationId,
            @RequestBody InvestmentRequest investmentRequest) {
        HttpSession httpSession = request.getSession();
        SessionUser user = (SessionUser) Optional.of(
                        httpSession.getAttribute(Constants.SESSION_NAME_USER))
                .orElseThrow(NotLoginException::new);

        Map resultmap = new HashMap<>();
        HttpStatus status;

        try {
            investService.investToStation(stationId, user.getId(),
                    investmentRequest.getInvestment());
            status = HttpStatus.OK;
        } catch (Exception e) {
            resultmap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map>(resultmap, status);
    }
}
