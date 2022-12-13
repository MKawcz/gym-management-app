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
    @JoinColumn(name="idClub")
    private Club club;

    @OneToOne(mappedBy = "manager")
    private Operator operator;

    public static Manager of(ManagerDto dto) {
        Manager manager = new Manager();
        manager.setFirstName(dto.getFirstName());
        manager.setLastName(dto.getLastName());
        manager.setSalary(dto.getSalary());

        return manager;
    }

    public void updateManager(ManagerDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }
}
