package pl.edu.pjatk.gymmanagementapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.model.Club;

@Data
public class ClubDto {

    private Long idClub;

    @NotBlank(message = "Field name cannot be blank and cannot be null")
    @Size(min=2, max=30, message = "Field name should be between 2 and 30 characters long")
    private String name;

    public static ClubDto of(Club club){
        ClubDto dto = new ClubDto();
        dto.setIdClub(club.getIdClub());
        dto.setName(club.getName());

        return dto;
    }
}
