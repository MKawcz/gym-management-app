package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClub;

    private String streetName;

    private String streetNumber;

    private String postalCode;

    private String City;

}
