package com.dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    @Getter @Setter private long id;

    @NotBlank
    @Column(name = "currency_name", nullable = false, unique = true)
    @Setter @Getter private String name;
}
