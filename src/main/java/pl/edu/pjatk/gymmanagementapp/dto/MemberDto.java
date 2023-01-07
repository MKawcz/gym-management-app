package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.anntotation.EnumNamePattern;
import pl.edu.pjatk.gymmanagementapp.model.Member;
import pl.edu.pjatk.gymmanagementapp.model.enums.MembershipType;

@Data
public class MemberDto {

    private Long idMember;

    @NotBlank(message = "Field firstName cannot be blank and cannot be null")
    @Size(min=2, max=50, message = "Field firstName should be between 2 and 50 characters long")
    private String firstName;

    @NotBlank(message = "Field lastName cannot be blank and cannot be null")
    @Size(min=2, max=50, message = "Field lastName should be between 2 and 50 characters long")
    private String lastName;

    @NotBlank(message = "Field email cannot be blank and cannot be null")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Provided email has an invalid format")
    private String email;

    @NotBlank(message = "Field password cannot be blank and cannot be null")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,16}$", message = "Password should be between 6 and 16 " +
            "characters long, contain at least 1 number and at least 1 uppercase letter")
    private String password;

    @EnumNamePattern(regexp = "DAILY|MONTHLY|SIX_MONTH|ANNUAL")                 //todo popraw walidacje
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
