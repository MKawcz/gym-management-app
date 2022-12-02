package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idManager;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @OneToOne(mappedBy = "manager")
    private Operator operator;

}
