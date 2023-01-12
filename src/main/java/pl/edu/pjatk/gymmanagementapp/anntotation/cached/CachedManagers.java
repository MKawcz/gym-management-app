package pl.edu.pjatk.gymmanagementapp.anntotation.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CachedManagers {

    private List<ManagerDto> managers;

}
