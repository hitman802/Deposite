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

    @Transient
    //@ManyToOne
    //@JoinColumn(name = "deposite_currency", referencedColumnName = "currency_id")
    @Setter @Getter private Currency currency;

    //@OneToMany(mappedBy = "deposite")
    @Transient
    @Setter @Getter private List<Replenishment> replenishments;

    @Column(name = "deposite_tax_on_percent")
    @Getter @Setter double taxOnPercents;

    @Column(name = "deposite_rate")
    @Getter @Setter double depositeRate;
}
