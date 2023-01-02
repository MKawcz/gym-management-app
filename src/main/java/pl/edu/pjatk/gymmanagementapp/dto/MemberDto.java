package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.entity.enums.MembershipType;

@Data
public class MemberDto {

    private Long idMember;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private MembershipType membershipType;

    public static MemberDto of(Member member) {
        MemberDto dto = new MemberDto();
        dto.setIdMember(member.getIdMember());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setMembershipType(member.getMembershipType());
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());

        return dto;
    }
}
