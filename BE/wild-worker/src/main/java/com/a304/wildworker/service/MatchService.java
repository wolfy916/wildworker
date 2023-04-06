package com.a304.wildworker.service;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activeuser.ActiveUser;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.common.Duel;
import com.a304.wildworker.domain.match.Match;
import com.a304.wildworker.domain.match.MatchRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.dto.request.MatchSelectRequest;
import com.a304.wildworker.dto.request.MiniGameProgressRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final DefaultMatchManager matchManager;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final ActiveUserRepository activeUserRepository;

    private Match getMatchFromActiveUser(ActiveUser activeUser) {
        String matchId = activeUser.getCurrentMatchId();
        return matchRepository.findById(matchId);
    }

    public void select(ActiveUser activeUser, MatchSelectRequest request) {
        Duel isDuel = request.isDuel() ? Duel.DUEL : Duel.RUN;
        Match match = getMatchFromActiveUser(activeUser);
        match.addSelected(activeUser.getUserId(), isDuel);
    }

    public void personalProgress(ActiveUser activeUser, MiniGameProgressRequest request) {
        int result = request.getResult();
        Match match = getMatchFromActiveUser(activeUser);
        match.addPersonalProgress(activeUser.getUserId(), result);
    }

    public void insertMatch(Match match) {
        matchRepository.save(match);
    }

    public void deleteMatch(String matchId) {
        matchRepository.deleteById(matchId);
    }

    public void makeMatch(ActiveStation activeStation) {
//        Stream<User> pool = activeStation.getPool().stream()
//                .map(userRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get);

        Stream<User> pool = activeStation.getPool().stream()
                .map(activeUserRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(activeUser -> Objects.equals(activeUser.getStationId(),
                        activeStation.getId()))
                .filter(activeUser -> activeUser.getCurrentMatchId() == null)
                .map(activeUser -> userRepository.findById(activeUser.getUserId()))
                .filter(Optional::isPresent)
                .map(Optional::get);

        matchManager.createMatches(pool, activeStation);
    }

}
