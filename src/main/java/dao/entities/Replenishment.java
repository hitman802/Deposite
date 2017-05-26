package dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Replenishment")
public class Replenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replenishment_id")
    @Setter @Getter private long id;

    @OneToOne
    @JoinColumn(name = "replenishment_currency", referencedColumnName = "currency_id")
    @Setter @Getter private Currency currency;

    @Column(name = "replenishment_sum")
    @Setter @Getter private double sum;

    @Column(name = "replenishment_date")
    @Setter @Getter Date date;

    @ManyToOne
    @JoinColumn(name = "replenishment_deposite", referencedColumnName = "deposite_id")
    @Getter @Setter Deposite deposite;
}
