package com.a304.wildworker.domain.match;

import com.a304.wildworker.exception.NoSuchMatchException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Getter
@Repository
public class MatchRepository {

    private final Map<String, Match> matchs;

    public MatchRepository() {
        matchs = new ConcurrentHashMap<>();
    }

    public Match findById(String id) {
        if (id == null) {
            throw new NoSuchMatchException("");
        }
        return Optional.ofNullable(matchs.get(id)).orElseThrow(() -> new NoSuchMatchException(id));
    }

    public Match save(Match match) {
        matchs.put(match.getId(), match);
        return match;
    }

    public void deleteById(String id) {
        matchs.remove(id);
    }

}
