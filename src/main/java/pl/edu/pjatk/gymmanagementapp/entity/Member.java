package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.enums.MembershipType;

@Entity
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMember;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_coach")
    private Coach coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_club")
    private Club club;

    public void of(MemberDto dto) {
        if(dto.getFirstName() != null) {
            this.setFirstName(dto.getFirstName());
        }
        if(dto.getLastName() != null) {
            this.setLastName(dto.getLastName());
        }
        if(dto.getMembershipType() != null) {
            this.setMembershipType(dto.getMembershipType());
        }
    }

}
