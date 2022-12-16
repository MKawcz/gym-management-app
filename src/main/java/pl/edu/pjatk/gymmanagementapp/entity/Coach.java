package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;

@Entity
@Data
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoach;

    @Embedded
    private Employee employee = new Employee();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

    public static Coach of(CoachDto dto) {
        Coach coach = new Coach();
        coach.getEmployee().setFirstName(dto.getFirstName());
        coach.getEmployee().setLastName(dto.getLastName());
        coach.getEmployee().setSalary(dto.getSalary());

        return coach;
    }

    public void updateCoach(CoachDto dto) {
        this.getEmployee().setFirstName(dto.getFirstName());
        this.getEmployee().setLastName(dto.getLastName());
        this.getEmployee().setSalary(dto.getSalary());
    }

}
