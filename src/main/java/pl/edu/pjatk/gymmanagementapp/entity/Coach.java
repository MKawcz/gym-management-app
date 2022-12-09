package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCoach;

    private String firstName;

    private String lastName;

    
}
