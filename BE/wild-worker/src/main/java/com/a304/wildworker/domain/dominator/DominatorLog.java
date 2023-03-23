package com.a304.wildworker.domain.dominator;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;

@Getter
@Entity
@Table(name = "dominator_log",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_dominator_log_station_id_dominate_start_time",
                        columnNames = {"station_id", "dominate_start_time"})})
public class DominatorLog extends BaseEntity {

    @ManyToOne(targetEntity = Station.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;
    @Column(name = "dominate_start_time", nullable = false)
    private String dominateStartTime;
}
