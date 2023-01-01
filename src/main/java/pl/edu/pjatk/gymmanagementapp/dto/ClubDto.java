package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Club;

@Data
public class ClubDto {

    private Long idClub;
    private String name;

    public static ClubDto of(Club club){
        ClubDto dto = new ClubDto();
        dto.setIdClub(club.getIdClub());
        dto.setName(club.getName());

        return dto;
    }
}
