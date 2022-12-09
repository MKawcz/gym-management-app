package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOperator;

    private String login;

    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idManager")
    private Manager manager;

}
