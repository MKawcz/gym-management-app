package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;

@Entity
@Data
public class Manager{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idManager;
    private String firstName;
    private String lastName;
    private Integer salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_club")
    private Club club;

    public void of(ManagerDto dto) {
        if(dto.getFirstName() != null) {
            this.setFirstName(dto.getFirstName());
        }
        if(dto.getLastName() != null) {
            this.setLastName(dto.getLastName());
        }

        if(dto.getSalary() != null) {
            this.setSalary(dto.getSalary());
        }
    }
}
