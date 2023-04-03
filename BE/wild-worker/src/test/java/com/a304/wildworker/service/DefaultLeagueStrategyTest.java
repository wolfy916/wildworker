package com.a304.wildworker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.a304.wildworker.domain.common.League;
import com.a304.wildworker.domain.match.strategy.DefaultLeagueStrategy;
import com.a304.wildworker.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DefaultLeagueStrategyTest {

    DefaultLeagueStrategy strategy = new DefaultLeagueStrategy();

    @Test
    public void testLeagueByCoinNone() {
        User user = new User("test");
        user.changeBalance(40L);
        League expected = League.NONE;
        League league = strategy.getLeague(user);
        assertEquals(expected, league);
    }

    @Test
    public void testLeagueByCoinLow() {
        User user = new User("test");
        user.changeBalance(100L);
        League expected = League.LOW;
        League league = strategy.getLeague(user);
        assertEquals(expected, league);
    }

    @Test
    public void testLeagueByCoinMid() {
        User user = new User("test");
        user.changeBalance(1001L);
        League expected = League.MIDDLE;
        League league = strategy.getLeague(user);
        assertEquals(expected, league);
    }

    @Test
    public void testLeagueByCoinHigh() {
        User user = new User("test");
        user.changeBalance(10011L);
        League expected = League.HIGH;
        League league = strategy.getLeague(user);
        assertEquals(expected, league);
    }

    @Test
    public void testLeagueByCoinTop() {
        User user = new User("test");
        user.changeBalance(100111L);
        League expected = League.TOP;
        League league = strategy.getLeague(user);
        assertEquals(expected, league);
    }


}
