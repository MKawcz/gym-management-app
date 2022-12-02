package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cleaner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCleaner;

    @Column
    private String firstName;

    @Column
    private String lastName;

}
