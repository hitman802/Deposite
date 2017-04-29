package dao.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Admin on 22.04.2017.
 */
@Entity
@Table(name = "Common")
public class Common {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CommonSeq")
    @SequenceGenerator(name = "CommonSeq", sequenceName="COMMON_SEQ", allocationSize=1)
    @Column(name = "id", unique = true)
    @Setter @Getter private Long id;

    @OneToMany
    @JoinColumn(name = "depositeid", referencedColumnName = "id")
    @Setter @Getter private List<Deposites> deposites;

    @OneToOne
    @JoinColumn(name = "name", referencedColumnName = "name")
    @Setter @Getter private User user;
}
