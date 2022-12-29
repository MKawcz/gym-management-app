package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.entity.MembershipType;

@Data
public class MemberDto {

    private Long idMember;
    private String firstName;
    private String lastName;

    public static MemberDto of(Member member) {
        MemberDto dto = new MemberDto();
        dto.setIdMember(member.getIdMember());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());

        return dto;
    }
}
