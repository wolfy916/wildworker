package com.a304.wildworker.domain.transaction;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.common.TransactionType;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private int value;
    @Enumerated
    @Column(nullable = false)
    private TransactionType type;
    @Column(nullable = false)
    private LocalDateTime appliedAt;
}
