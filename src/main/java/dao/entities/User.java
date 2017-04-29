package dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "users")
public class User {

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
    @Setter @Getter private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles", referencedColumnName = "id")
    @Getter @Setter private Set<UserRole> userRoles;
}
