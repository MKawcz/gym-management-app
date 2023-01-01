package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;

import java.util.List;

@Entity
@Data
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoach;
    private String firstName;
    private String lastName;
    private Integer salary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coach", orphanRemoval = true)
    private List<Member> members;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_club")
    private Club club;

    public void of(CoachDto dto) {
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
