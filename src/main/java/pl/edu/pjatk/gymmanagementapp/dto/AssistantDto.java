package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Assistant;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;

@Data
public class AssistantDto {

    private Long idAssistant;
    private String firstName;
    private String lastName;
    private Integer salary;

    public static AssistantDto of(Assistant assistant) {
        AssistantDto dto = new AssistantDto();
        dto.setIdAssistant(assistant.getIdAssistant());
        dto.setFirstName(assistant.getFirstName());
        dto.setLastName(assistant.getLastName());
        dto.setSalary(assistant.getSalary());

        return dto;
    }

}
