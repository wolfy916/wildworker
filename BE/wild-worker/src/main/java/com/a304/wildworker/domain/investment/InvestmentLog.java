package com.a304.wildworker.domain.investment;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "investment_log")
public class InvestmentLog extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false)
    private User user;
    @ManyToOne(targetEntity = Station.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Station station;
    @Column(nullable = false)
    private Long amount;
}
