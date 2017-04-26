package dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
public class Replenishment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReplenishmentSeq")
    @SequenceGenerator(name = "ReplenishmentSeq", sequenceName="REPLENISHMENT_SEQ", allocationSize=1)
    @Column(name = "id", unique = true)
    @Setter @Getter private Long id;

    @ManyToOne
    @JoinColumn(name = "currency", referencedColumnName = "name")
    @Setter @Getter private Currency currency;

    @Column(name = "sum")
    @Setter @Getter private double sum;

    @Column(name = "date")
    @Setter @Getter Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Replenishment that = (Replenishment) o;

        if (Double.compare(that.sum, sum) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Replenishment{" +
                "id=" + id +
                ", currency=" + currency +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }
}
