package pl.edu.pjatk.gymmanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubModuleDto {

    private ClubDto selectedClub;
    private List<ClubDto> clubDtoList;
    private List<ManagerDto> managerDtoList;
    private List<CoachDto> coachDtoList;


}
