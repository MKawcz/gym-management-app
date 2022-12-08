package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClub;

    @Column
    private String streetName;

    @Column
    private String streetNumber;

    @Column
    private String postalCode;

    @Column
    private String City;

}
