package com.a304.wildworker.service;

import com.a304.wildworker.domain.activestation.ActiveStation;
import com.a304.wildworker.domain.activeuser.ActiveUserRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.service.factory.DefaultMatchManager;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final DefaultMatchManager matchManager;
    private final UserRepository userRepository;
    private final ActiveUserRepository activeUserRepository;

    public void makeMatch(ActiveStation activeStation) {
        Stream<User> pool = activeStation.getPool().stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get);

//        Stream<User> pool = activeStation.getPool().stream()
//                .map(activeUserRepository::findById)
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .filter(activeUser -> Objects.equals(activeUser.getStationId(),
//                        activeStation.getId()))
//                .filter(activeUser -> activeUser.getCurrentMatchId() == null)
//                .map(activeUser -> userRepository.findById(activeUser.getUserId()))
//                .filter(Optional::isPresent)
//                .map(Optional::get);

        matchManager.createMatches(pool, activeStation);
    }

}
