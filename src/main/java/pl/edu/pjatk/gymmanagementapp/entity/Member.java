package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;

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
    @JoinColumn(name="idCoach")
    private Coach coach;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

    public static Member of(MemberDto dto) {
        Member member = new Member();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());

        return member;
    }

}
