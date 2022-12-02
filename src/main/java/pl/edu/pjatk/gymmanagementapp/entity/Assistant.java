package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAssistant;

    @Column
    private String firstName;

    @Column
    private String lastName;

}
