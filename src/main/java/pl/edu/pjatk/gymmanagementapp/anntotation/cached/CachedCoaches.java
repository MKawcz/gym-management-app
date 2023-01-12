package pl.edu.pjatk.gymmanagementapp.anntotation.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CachedCoaches {

    private List<CoachDto> coaches;

}
