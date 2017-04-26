package dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
public class Users {

    @Id
    @Size(min=1)
    @Column(name = "name", nullable = false, insertable = true, updatable = false, unique = true)
    @NotBlank
    @Setter @Getter private String name;

    @Size(min=1)
    @Column(name = "email", nullable = false, insertable = true, updatable = false, unique = true)
    @NotBlank
    @Setter @Getter private String email;

    @NotBlank
    @Size(min=1)
    @Column(name = "password", nullable = false, insertable = true, updatable = true)
    private String password;
    private String matchingPassword;
}
