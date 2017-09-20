package com.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Admin on 26.04.2017.
 */
@ToString
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    @Getter @Setter private long id;

    @Column(name = "role_name", nullable = false, unique = true)
    @Getter @Setter private String name;

    @ManyToMany(mappedBy = "roles")
    @Getter @Setter private Set<Users> users;
}
