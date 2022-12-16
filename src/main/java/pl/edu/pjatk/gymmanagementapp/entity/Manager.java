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

    @Embedded
    private Employee employee = new Employee();

    @OneToOne(mappedBy = "manager")
    private Operator operator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

    public static Manager of(ManagerDto dto) {
        Manager manager = new Manager();
        manager.getEmployee().setFirstName(dto.getFirstName());
        manager.getEmployee().setLastName(dto.getLastName());
        manager.getEmployee().setSalary(dto.getSalary());

        return manager;
    }

    public void updateManager(ManagerDto dto) {
        this.getEmployee().setFirstName(dto.getFirstName());
        this.getEmployee().setLastName(dto.getLastName());
        this.getEmployee().setSalary(dto.getSalary());
    }
}
