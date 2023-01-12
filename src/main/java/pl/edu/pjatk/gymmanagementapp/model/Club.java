package pl.edu.pjatk.gymmanagementapp.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.ClubDto;
import pl.edu.pjatk.gymmanagementapp.exception.NoSuchClubException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Entity
@Data
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Manager> managers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Coach> coaches;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
    private List<Member> members;

    public void of(ClubDto dto) {
        this.setName(dto.getName());
    }


}
