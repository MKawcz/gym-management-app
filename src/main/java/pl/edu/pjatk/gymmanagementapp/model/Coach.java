package pl.edu.pjatk.gymmanagementapp.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;

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
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }
}
