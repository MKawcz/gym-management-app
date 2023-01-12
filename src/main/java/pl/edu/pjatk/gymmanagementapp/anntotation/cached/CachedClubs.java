package pl.edu.pjatk.gymmanagementapp.anntotation.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.ClubDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CachedClubs {

    private List<ClubDto> clubs;

}
