package com.a304.wildworker.domain.user;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.common.TitleType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(onlyExplicitlyIncluded = true)
public class User extends BaseEntity {

    @Column(nullable = false)
    private Role role;
    @ToString.Include
    @Column(unique = true, nullable = false)
    private String email;
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
    //    @ManyToOne(targetEntity = Title.class, fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    private Title title;
    private Long title_id;  // TODO
    @Column(nullable = false)
    private int numberOfCollectedPaper;
    private boolean deleted;
    private LocalDateTime deletedAt;

//    @OneToMany(mappedBy = "user")
//    private List<TitleAwarded> titleAwardeds = new ArrayList<>();

    public User(String email, Role role, String wallet) {
        this.role = role;
        this.email = email;
        this.name = email;
        this.wallet = wallet;
        this.balance = 0L;
        this.characterId = CharacterType.MAN;
        this.titleType = TitleType.TITLE;
        this.title_id = Constants.noneTitle;
        this.numberOfCollectedPaper = 0;
    }
}
