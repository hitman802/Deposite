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
@Table(name = "Deposites")
public class Deposites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DepositesSeq")
    @SequenceGenerator(name = "DepositesSeq", sequenceName="DEPOSITES_SEQ", allocationSize=1)
    @Column(name = "id", unique = true)
    @Setter @Getter private Long id;

    @Column(name = "startDate")
    @Getter @Setter private Date startDate;

    @Column(name = "endDate")
    @Getter @Setter private Date endDate;

    @ManyToOne
    @JoinColumn(name = "currency", referencedColumnName = "name")
    @Setter @Getter private Currency currency;

    @OneToMany
    @JoinColumn(name = "replenishment", referencedColumnName = "id")
    @Setter @Getter private List<Replenishment> replenishments;

    @Column(name = "TaxOnPercents")
    @Getter @Setter double taxOnPersents;

    @Column(name = "depositeRate")
    @Getter @Setter double depositeRate;
}
