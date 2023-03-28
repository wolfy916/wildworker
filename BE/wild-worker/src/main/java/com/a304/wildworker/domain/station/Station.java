package com.a304.wildworker.domain.station;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.location.Location;
import com.a304.wildworker.domain.user.User;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
    private Map<User, Long> investors = new HashMap<>();

    public void invest(User user, Long amount) {
        investors.put(user, investors.getOrDefault(user, 0L) + amount);
    }

    public void resetCommission() {
        this.commission = 0L;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 이 메소드는 실제 블록체인 네트워크에 기록된 내용이 아니므로 정확하지 않을 수 있음(소수점 처리 과정이 정확하지 않을 수 있음)
     */
    public void distributeCommission() {
        for (Entry<User, Long> entry : investors.entrySet()) {
            User user = entry.getKey();
            Long investment = entry.getValue();

            long userShare = (investment / this.balance) * 1000;
            long money = (userShare * this.commission) / 1000;

            user.addBalance(money);
        }
    }
}
