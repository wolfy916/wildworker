package com.a304.wildworker.domain.minigame;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.MiniGameType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "mini_game")
public class MiniGame extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private MiniGameType code;
    @Column(nullable = false, unique = true)
    private String name;
}
