package com.a304.wildworker.domain.location;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location {

    @Column(name = "location_lat", nullable = false)
    private double lat;
    @Column(name = "location_lon", nullable = false)
    private double lon;
}
