package com.a304.wildworker.domain.match;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class MatchRepository {

    private Map<String, Match> matchs;

    public MatchRepository() {
        matchs = new ConcurrentHashMap<>();
    }

    public Match save(Match match) {
        matchs.put(match.getId(), match);
        return match;
    }

    public void deleteById(String id) {
        matchs.remove(id);
    }

}
