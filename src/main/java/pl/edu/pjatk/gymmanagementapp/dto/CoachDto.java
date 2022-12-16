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
        dto.setFirstName(coach.getEmployee().getFirstName());
        dto.setLastName(coach.getEmployee().getLastName());
        dto.setSalary(coach.getEmployee().getSalary());

        return dto;
    }

}
