package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.anntotation.EnumNamePattern;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.model.enums.MembershipType;

@Data
public class MemberDto {

    private Long idMember;

    @NotBlank(message = "Field firstName cannot be blank and cannot be null")
    @Size(max=50, message = "Field firstName should be max and 50 characters long")
    private String firstName;

    @NotBlank(message = "Field lastName cannot be blank and cannot be null")
    @Size(max=50, message = "Field lastName should be max 50 characters long")
    private String lastName;

    @EnumNamePattern(regexp = "DAILY|MONTHLY|SIX_MONTH|ANNUAL")   //todo popraw walidacje
    private MembershipType membershipType;

    public static MemberDto of(Member member) {
        MemberDto dto = new MemberDto();
        dto.setIdMember(member.getIdMember());
        dto.setFirstName(member.getFirstName());
        dto.setLastName(member.getLastName());
        dto.setMembershipType(member.getMembershipType());

        return dto;
    }
}
