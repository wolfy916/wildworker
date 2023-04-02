package com.a304.wildworker.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.DefaultMatch;
import com.a304.wildworker.domain.match.Match;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DefaultMatchTest {

    @Test
    void testCreateDefaultMatch() {
        Match match = new DefaultMatch(List.of(), League.LOW);
        assertEquals(DefaultMatch.defaultCost, match.getCost());
        assertEquals(DefaultMatch.defaultRunCost, match.getRunCost());
    }

    @Test
    void testCreateDefaultMatchMiddleLeague() {
        int expectedCost = 200;
        int expectedRunCost = 50;
        Match match = new DefaultMatch(List.of(), League.MIDDLE);
        assertEquals(expectedCost, match.getCost());
        assertEquals(expectedRunCost, match.getRunCost());
    }

    @Test
    void testCreateDefaultMatchHighLeague() {
        int expectedCost = 2000;
        int expectedRunCost = 500;
        Match match = new DefaultMatch(List.of(), League.HIGH);
        assertEquals(expectedCost, match.getCost());
        assertEquals(expectedRunCost, match.getRunCost());
    }

    @Test
    void testCreateDefaultMatchTopLeague() {
        int expectedCost = 20000;
        int expectedRunCost = 5000;
        Match match = new DefaultMatch(List.of(), League.TOP);
        assertEquals(expectedCost, match.getCost());
        assertEquals(expectedRunCost, match.getRunCost());
    }
}
