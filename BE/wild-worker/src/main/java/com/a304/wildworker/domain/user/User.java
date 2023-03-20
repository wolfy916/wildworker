package com.a304.wildworker.domain.user;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.common.TitleType;
import com.a304.wildworker.domain.title.Title;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class User extends BaseEntity {

    @Column(nullable = false)
    private Role role;
    @Column(unique = true, nullable = false)
    private String kakaoEmail;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String wallet;      //TODO: chang wallet type
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long balance;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private CharacterType characterId;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TitleType titleType;
    @ManyToOne(targetEntity = Title.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Title title;
    @Column(nullable = false)
    private int numberOfCollectedPaper;
    private boolean deleted;
    private LocalDateTime deletedAt;

//    @OneToMany(mappedBy = "user")
//    private List<TitleAwarded> titleAwardeds = new ArrayList<>();
}
