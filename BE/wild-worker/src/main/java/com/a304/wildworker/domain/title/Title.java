package com.a304.wildworker.domain.title;

import com.a304.wildworker.domain.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "title")
public class Title extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;
    private int condition1;
    @Column(nullable = true)
    private int condition2;
    @Column(nullable = true)
    private int condition3;
    @Column(nullable = true)
    private int condition4;
    @Column(nullable = true)
    private int condition5;
}
