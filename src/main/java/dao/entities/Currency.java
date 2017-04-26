package dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @Size(min=1)
    @Column(name = "name", nullable = false, insertable = true, updatable = false, unique = true)
    @NotBlank
    @Setter @Getter private String id;

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Currency currency = (Currency) o;

        return id.equals(currency.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
