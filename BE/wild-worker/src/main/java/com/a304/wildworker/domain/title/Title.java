package com.a304.wildworker.domain.title;

import com.a304.wildworker.domain.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Title extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

}
