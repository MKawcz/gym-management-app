package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoach;

    private String firstName;

    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;
    
}
