package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idManager;

    private String firstName;

    private String lastName;

    @OneToOne(mappedBy = "manager")
    private Operator operator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

}
