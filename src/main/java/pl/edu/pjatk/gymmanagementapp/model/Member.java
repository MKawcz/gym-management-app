package pl.edu.pjatk.gymmanagementapp.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.model.enums.MembershipType;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMember;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    //todo hashowanie password

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_coach")
    private Coach coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_club")
    private Club club;

    public void of(MemberDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setMembershipType(dto.getMembershipType());
        this.setEmail(dto.getEmail());
        this.setPassword(dto.getPassword());
    }
}
