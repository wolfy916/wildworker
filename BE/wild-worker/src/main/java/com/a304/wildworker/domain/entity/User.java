package com.a304.wildworker.domain.entity;

import com.a304.wildworker.common.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private Role role;
}
