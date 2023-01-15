package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.model.Coach;

@Data
public class CoachDto {

    private Long idCoach;

    @NotBlank(message = "Field firstName cannot be blank and cannot be null")
    @Size(max=50, message = "Field firstName should be max 50 characters long")
    private String firstName;

    @NotBlank(message = "Field lastName cannot be blank and cannot be null")
    @Size(min=2, max=50, message = "Field lastName should be max 50 characters long")
    private String lastName;

    @NotNull(message = "Field salary cannot be null")
    private Integer salary;

    public static CoachDto of(Coach coach) {
        CoachDto dto = new CoachDto();
        dto.setIdCoach(coach.getIdCoach());
        dto.setFirstName(coach.getFirstName());
        dto.setLastName(coach.getLastName());
        dto.setSalary(coach.getSalary());

        return dto;
    }
}
