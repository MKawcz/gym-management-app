package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cleaner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCleaner;

    private String firstName;

    private String lastName;

}
