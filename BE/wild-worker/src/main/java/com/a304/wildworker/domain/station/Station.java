package com.a304.wildworker.domain.station;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.domain.user.User;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    @Transient
    private Map<User, Long> investors = new ConcurrentHashMap<>();

    @Transient
    private AtomicLong prevCommission = new AtomicLong(0L);

    public void invest(User user, Long amount) {
        this.balance += amount;
        investors.put(user, investors.getOrDefault(user, 0L) + amount);
    }

    public void resetCommission() {
        prevCommission.set(this.commission);
        this.commission = 0L;
    }

    public void resetBalance() {
        this.balance = 0L;
        investors.clear();
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
