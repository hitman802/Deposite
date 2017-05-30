package dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Users")
public class Users {

    @Id
    @Column(name = "users_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter private long id;

    @NotBlank
    @Column(name = "users_name", nullable = false, unique = true)
    @Getter @Setter private String name;

    @NotBlank
    @Column(name = "users_password", nullable = false)
    @Setter @Getter private String password;

    @NotBlank
    @Column(name = "users_email", nullable = false, unique = true)
    @Setter @Getter private String email;

    @Transient
    @Setter @Getter private String passwordConfirm;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Getter @Setter private Set<Deposite> deposits;

    //3rd table for many to many relation
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usersrole",
        joinColumns = @JoinColumn(name = "usersrole_user"),
        inverseJoinColumns = @JoinColumn(name = "usersrole_role")
    )
    @Getter @Setter private Set<Role> roles;
}