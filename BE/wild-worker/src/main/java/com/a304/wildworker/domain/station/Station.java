package com.a304.wildworker.domain.station;

import com.a304.wildworker.domain.common.BaseEntity;
import com.a304.wildworker.domain.location.Location;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Station extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;         //TODO
    @Column(nullable = false)
    private Location location;
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long balance;
    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long commission;
}
