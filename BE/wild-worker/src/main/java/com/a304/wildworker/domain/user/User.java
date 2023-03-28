package com.a304.wildworker.domain.user;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.common.TitleType;
import com.a304.wildworker.ethereum.exception.WalletCreationException;
import com.a304.wildworker.ethereum.service.WalletProvider;
import com.a304.wildworker.exception.NotEnoughBalanceException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
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
@Table(name = "user")
public class User extends BaseEntity {

    @Column(nullable = false)
    private Role role;
    @ToString.Include
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String wallet;      // blockchain wallet file path
    @Column(unique = true, nullable = false)
    private String walletPassword; // key for blockchain wallet
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

    public User(String email) throws WalletCreationException {
        this.role = Role.ROLE_USER;
        this.email = email;
        this.name = email;
        this.walletPassword = UUID.randomUUID().toString(); // TODO: 2023-03-23 초기화 시 암호화 필요함
        this.wallet = WalletProvider.createUserWallet(this.walletPassword);
        this.balance = 0L;
        this.characterId = CharacterType.MAN;
        this.titleType = TitleType.TITLE;
        this.title_id = Constants.noneTitle;
        this.numberOfCollectedPaper = 0;
    }

    public int collectPaper() {
        return ++this.numberOfCollectedPaper;
    }

    public void sellPaper() {
        this.numberOfCollectedPaper = 0;
    }

    public void invest(long amount) {
        if (this.balance < amount) {
            throw new NotEnoughBalanceException();
        }
        this.balance -= amount;
    }
}
