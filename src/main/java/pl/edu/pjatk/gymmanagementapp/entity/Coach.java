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

    private String firstName;

    private String lastName;

    private Integer salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

    public static Coach of(CoachDto dto) {
        Coach coach = new Coach();
        coach.setFirstName(dto.getFirstName());
        coach.setLastName(dto.getLastName());
        coach.setSalary(dto.getSalary());

        return coach;
    }

    public void updateCoach(CoachDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }

}
