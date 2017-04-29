package dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Admin on 26.04.2017.
 */
@Entity
@Table(name = "userrole")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter private Long id;

    @Column(name = "name")
    @Getter @Setter private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", referencedColumnName = "name")
    @Getter @Setter private Set<User> users;
}
