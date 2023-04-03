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
    private Integer condition1;
    @Column(nullable = true)
    private Integer condition2;
    @Column(nullable = true)
    private Integer condition3;
    @Column(nullable = true)
    private Integer condition4;
    @Column(nullable = true)
    private Integer condition5;
}
