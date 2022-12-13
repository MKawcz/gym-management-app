package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;

    private String streetName;

    private String streetNumber;

    private String postalCode;

    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Manager> managers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Coach> coaches;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Assistant> assistants;


    //todo stwórz klase address
}
