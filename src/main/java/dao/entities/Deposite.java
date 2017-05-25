package dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Deposite")
public class Deposite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposite_id")
    @Setter @Getter private Long id;

    @Column(name = "deposite_date_start")
    @Getter @Setter private Date startDate;

    @Column(name = "deposite_date_end")
    @Getter @Setter private Date endDate;

    @Column(name = "deposite_sum")
    @Getter @Setter private double sum;

    @OneToOne
    //@Column(name = "deposite_currency")
    @Setter @Getter private Currency currency;

    @OneToMany
    @Column(name = "deposite_replenishment")
    @Setter @Getter private List<Replenishment> replenishments;

    @Column(name = "deposite_tax_on_percent")
    @Getter @Setter double taxOnPercents;

    @Column(name = "deposite_rate")
    @Getter @Setter double depositeRate;

    @OneToOne
    //@Column(name = "deposite_user")
    @Getter @Setter Users user;
}
