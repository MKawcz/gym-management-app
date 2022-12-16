package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;

@Data
public class ManagerDto {

    private Long idManager;
    private String firstName;
    private String lastName;
    private Integer salary;

    public static ManagerDto of(Manager manager) {
        ManagerDto dto = new ManagerDto();
        dto.setIdManager(manager.getIdManager());
        dto.setFirstName(manager.getEmployee().getFirstName());
        dto.setLastName(manager.getEmployee().getLastName());
        dto.setSalary(manager.getEmployee().getSalary());

        return dto;
    }

}
