package com.a304.wildworker.domain.station;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.location.Location;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "station")
public class Station extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Location location;
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long balance;
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long commission;

    public void invest(Long amount) {
        this.balance += amount;
    }

    public void resetCommission() {
        this.commission = 0L;
    }

    public void resetBalance() {
        this.balance = 0L;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
