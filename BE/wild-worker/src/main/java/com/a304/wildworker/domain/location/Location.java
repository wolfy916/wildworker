package com.a304.wildworker.domain.location;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {

    @Column(name = "location_x", nullable = false)
    private double x;
    @Column(name = "location_y", nullable = false)
    private double y;
}
