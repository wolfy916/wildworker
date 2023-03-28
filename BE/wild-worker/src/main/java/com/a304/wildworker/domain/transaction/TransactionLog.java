package com.a304.wildworker.domain.transaction;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "transaction_log")
public class TransactionLog extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne(targetEntity = Station.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Station station;
    @Column(nullable = false)
    private Long value;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TransactionType type;
    private LocalDateTime appliedAt;

    public TransactionLog(User user, Station station, Long value, TransactionType type) {
        this.user = user;
        this.station = station;
        this.value = value;
        this.type = type;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
