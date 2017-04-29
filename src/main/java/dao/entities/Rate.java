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
@Table(name = "rates")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RateSeq")
    @SequenceGenerator(name = "RateSeq", sequenceName="RATE_SEQ", allocationSize=1)
    @Column(name = "id", unique = true)
    @Setter @Getter private Long id;

    @JsonProperty("cc")
    @Transient
    @Getter private String name;

    @ManyToOne
    @JoinColumn(name = "currency", referencedColumnName = "name")
    @Setter @Getter private Currency currency;

    @JsonProperty("rate")
    @Column(name = "rate")
    @Setter @Getter private double rate;

    @OneToOne
    @JoinColumn(name = "source", referencedColumnName = "name")
    @Setter @Getter private RateSource source;

    @Column(name = "date", unique = true)
    @Setter @Getter private Date date;

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                ", rate=" + rate +
                ", date=" + date +
                ", source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rate rate1 = (Rate) o;

        if (Double.compare(rate1.rate, rate) != 0) return false;
        if (id != null ? !id.equals(rate1.id) : rate1.id != null) return false;
        if (name != null ? !name.equals(rate1.name) : rate1.name != null) return false;
        if (currency != null ? !currency.equals(rate1.currency) : rate1.currency != null) return false;
        return date != null ? date.equals(rate1.date) : rate1.date == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        temp = Double.doubleToLongBits(rate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
