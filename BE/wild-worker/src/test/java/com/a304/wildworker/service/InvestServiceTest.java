package com.a304.wildworker.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.response.InvestmentRankResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(MockitoJUnitRunner.class)
public class InvestServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    InvestService investService;

    @BeforeEach
    public void init() {
//       investService  = new InvestService(null, userRepository, null, null, null, null, null, null, null);

    }
    @Test
    public void setDominatorFirstIfExistInRankListTest() {
        User dominator = new User("dominator");
        dominator.setId(0L);
        when(userRepository.findById(dominator.getId()))
                .thenReturn(Optional.of(dominator));

        Station station = new Station();
        station.invest(1000L);

        Map<Long, Long> investors = new HashMap<>();
        for (Long i = 1L; i <= 5L; i++) {
            investors.put(i, 100L);
        }
        investors.put(dominator.getId(), 100L);

        Entry<Long, Long> dominatorInvestInfo = null;
        List<InvestmentRankResponse> rankList = new ArrayList<>(5);

        List<Entry<Long, Long>> investInfoList = new ArrayList<>(investors.entrySet());
        for (Entry<Long, Long> investInfo : investInfoList) {
            if (investInfo.getKey().equals(dominator.getId())) {
                dominatorInvestInfo = investInfo;
            } else {
                rankList.add(getBuild(investInfo));
            }
        }

        investService.setDominatorFirstIfExistInRankList(dominator.getId(), dominatorInvestInfo, rankList, investInfoList, station);

        assertTrue(rankList.get(0).getName().equals(dominator.getName()));
    }

    private static InvestmentRankResponse getBuild(Entry<Long, Long> investInfo) {
        return InvestmentRankResponse.builder()
                .name("이름")
                .investment(investInfo.getValue())
                .percent(100)
                .build();
    }
}
