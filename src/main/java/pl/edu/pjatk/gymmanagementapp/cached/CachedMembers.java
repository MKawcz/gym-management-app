package pl.edu.pjatk.gymmanagementapp.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CachedMembers {

    private List<MemberDto> members;

}
