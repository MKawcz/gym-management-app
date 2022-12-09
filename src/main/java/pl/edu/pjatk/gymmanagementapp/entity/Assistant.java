package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAssistant;

    private String firstName;

    private String lastName;

}
