package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;

@Data
public class CoachDto {

    private Long idCoach;
    private String firstName;
    private String lastName;
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
