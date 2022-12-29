package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.ClubDto;

import java.util.List;

@Entity
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;

    private String name;

    private String streetName;

    private String streetNumber;

    private String postalCode;

    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Manager> managers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Coach> coaches;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Member> members;

    public static Club of(ClubDto dto) {
        Club club = new Club();
        club.setName(dto.getName());
        club.setStreetName(dto.getStreetName());
        club.setStreetNumber(dto.getStreetNumber());
        club.setPostalCode(dto.getPostalCode());
        club.setCity(dto.getCity());

        return club;
    }

    //todo stwórz klase address
}
