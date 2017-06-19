package dao.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import deposit.DepositOperation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Deposite")
public class Deposite {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deposite_id")
    @Setter @Getter private Long id;

    @JsonProperty("name")
    @Column(name = "deposite_name")
    @Getter @Setter private String name;

    @Column(name = "deposite_date_start")
    @Getter @Setter private Date startDate;


    @Column(name = "deposite_date_end")
    @Getter @Setter private Date endDate;

    @Column(name = "deposite_sum")
    @Getter @Setter private double sum;

    @OneToOne
    @JoinColumn(name = "deposite_currency", referencedColumnName = "currency_id")
    @Setter @Getter private Currency currency;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "deposite")
    @Setter @Getter private List<Replenishment> replenishments;

    @Column(name = "deposite_tax_on_percent")
    @Getter @Setter double taxOnPercents;

    @Column(name = "deposite_rate")
    @Getter @Setter double depositeRate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "deposite_user", referencedColumnName = "users_id")
    @Getter @Setter Users user;

    @Transient
    @Getter @Setter private Collection<DepositOperation> depositOperations;

    @Transient
    @Getter @Setter private Double finalSum;
}
