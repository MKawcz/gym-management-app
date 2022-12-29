package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.entity.Club;

@Data
public class ClubDto {

    private Long idClub;
    private String name;
    private String streetName;
    private String streetNumber;
    private String postalCode;
    private String city;

    public static ClubDto of(Club club){
        ClubDto dto = new ClubDto();
        dto.setIdClub(club.getIdClub());
        dto.setName(club.getName());
        dto.setStreetName(club.getStreetName());
        dto.setStreetNumber(club.getStreetNumber());
        dto.setPostalCode(club.getPostalCode());
        dto.setCity(club.getCity());

        return dto;
    }

}
