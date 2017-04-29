package dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created by Admin on 25.04.2017.
 */
@Entity
@Table(name = "rateSource")
public class RateSource {

    @Id
    @Size(min=1)
    @Column(name = "name", nullable = false, insertable = true, updatable = false, unique = true)
    @NotBlank
    @Setter @Getter private String name;

}
