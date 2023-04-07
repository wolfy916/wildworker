package com.a304.wildworker.domain.user;

import com.a304.wildworker.common.Constants;
import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.CharacterType;
import com.a304.wildworker.domain.common.Role;
import com.a304.wildworker.domain.common.TitleCode;
import com.a304.wildworker.domain.common.TitleShowType;
import com.a304.wildworker.ethereum.exception.WalletCreationException;
import com.a304.wildworker.ethereum.service.WalletProvider;
import com.a304.wildworker.exception.NotEnoughBalanceException;
import com.a304.wildworker.exception.PaperTooLowException;
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
import lombok.Setter;
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
    @Setter
    @Column(unique = true, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String wallet;      // blockchain wallet file path
    @Column(unique = true, nullable = false)
    private String walletPassword; // key for blockchain wallet
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long balance;
    @Setter
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private CharacterType characterId;
    @Setter
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TitleShowType titleShowType;
    @Setter
    private Long titleId;
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
        this.titleShowType = TitleShowType.TITLE;
        this.titleId = TitleCode.NONE.getId();
        this.numberOfCollectedPaper = 0;
    }

    public int collectPaper() {
        return ++this.numberOfCollectedPaper;
    }

    public void sellPaper() {
        if (this.numberOfCollectedPaper < Constants.SELL_LIMIT) {
            throw new PaperTooLowException();
        }
        this.numberOfCollectedPaper = 0;
        this.balance += Constants.AMOUNT_MANUAL_MINE;
    }

    public void invest(long amount) {
        if (this.balance < amount) {
            throw new NotEnoughBalanceException(balance, amount);
        }
        this.balance -= amount;
    }

    public Long changeBalance(Long value) {
        if (this.balance + value < 0) {
            throw new NotEnoughBalanceException(balance, value);
        }
        return this.balance += value;
    }
}
