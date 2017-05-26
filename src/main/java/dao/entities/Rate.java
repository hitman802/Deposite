package dao.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Rate")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id", unique = true)
    @Setter @Getter private long id;

    @JsonProperty("cc")
    @Transient
    @Getter private String name;

    @JsonProperty("rate")
    @Column(name = "rate_rate")
    @Setter @Getter private double rate;

    @Column(name = "rate_date")
    @Setter @Getter private Date date;

    @OneToOne
    @JoinColumn(name = "rate_currency", referencedColumnName = "currency_id")
    @Setter @Getter private Currency currency;

    @OneToOne
    @JoinColumn(name = "rate_ratesource", referencedColumnName = "ratesource_id")
    @Setter @Getter private RateSource source;
}
