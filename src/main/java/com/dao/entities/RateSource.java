package com.dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by Admin on 25.04.2017.
 */
@Entity
@Table(name = "ratesource")
public class RateSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ratesource_id")
    @Getter @Setter private long id;

    @NotBlank
    @Column(name = "ratesource_name", nullable = false, unique = true)
    @Setter @Getter private String name;

}
