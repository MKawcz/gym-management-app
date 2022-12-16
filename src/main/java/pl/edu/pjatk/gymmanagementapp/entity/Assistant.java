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

    @Embedded
    private Employee employee = new Employee();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idClub")
    private Club club;


    public static Assistant of(AssistantDto dto) {
        Assistant assistant = new Assistant();
        assistant.getEmployee().setFirstName(dto.getFirstName());
        assistant.getEmployee().setLastName(dto.getLastName());
        assistant.getEmployee().setSalary(dto.getSalary());

        return assistant;
    }

    public void updateAssistant(AssistantDto dto) {
        this.getEmployee().setFirstName(dto.getFirstName());
        this.getEmployee().setLastName(dto.getLastName());
        this.getEmployee().setSalary(dto.getSalary());
    }

}
