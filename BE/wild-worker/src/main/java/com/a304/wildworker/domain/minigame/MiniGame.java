package com.a304.wildworker.domain.minigame;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.MiniGameCode;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "mini_game")
public class MiniGame extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private MiniGameCode code;
    @Column(nullable = false, unique = true)
    private String name;

    public Long getWinner(Map<Long, Integer> progress) {
        return progress.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}
