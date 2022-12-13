package pl.edu.pjatk.gymmanagementapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.AssistantDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;

@Entity
@Data
public class Assistant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssistant;

    private String firstName;

    private String lastName;

    private Integer salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;

    public static Assistant of(AssistantDto dto) {
        Assistant assistant = new Assistant();
        assistant.setFirstName(dto.getFirstName());
        assistant.setLastName(dto.getLastName());
        assistant.setSalary(dto.getSalary());

        return assistant;
    }

    public void updateAssistant(AssistantDto dto) {
        this.setFirstName(dto.getFirstName());
        this.setLastName(dto.getLastName());
        this.setSalary(dto.getSalary());
    }

}
