package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.model.Manager;

@Data
public class ManagerDto {

    private Long idManager;

    @NotBlank(message = "Field firstName cannot be blank and cannot be null")
    @Size(max=50, message = "Field firstName should be max 50 characters long")
    private String firstName;

    @NotBlank(message = "Field lastName cannot be blank and cannot be null")
    @Size(min=2, max=50, message = "Field lastName should be max 50 characters long")
    private String lastName;

    @NotNull(message = "Field salary cannot be null")
    private Integer salary;

    public static ManagerDto of(Manager manager) {
        ManagerDto dto = new ManagerDto();
        dto.setIdManager(manager.getIdManager());
        dto.setFirstName(manager.getFirstName());
        dto.setLastName(manager.getLastName());
        dto.setSalary(manager.getSalary());

        return dto;
    }

}
