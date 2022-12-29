package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public MemberDto saveMember(MemberDto dto) {
        return MemberDto.of(repository.save(Member.of(dto)));
    }

    public List<MemberDto> getAllMembers() {
        return repository.findAll().stream().map(MemberDto::of).toList();
    }

    public MemberDto updateMember(long memberId, MemberDto updatedDto) {
        var optionalMember = repository.findById(memberId);
        if(optionalMember.isPresent()) {
            Member memberToUpdate = optionalMember.get();

            if(updatedDto.getFirstName() != null) {
                memberToUpdate.setFirstName(updatedDto.getFirstName());
            }

            if(updatedDto.getLastName() != null) {
                memberToUpdate.setLastName(updatedDto.getLastName());
            }

            return MemberDto.of(repository.save(memberToUpdate));
        }

        throw new RuntimeException("Member with the given id does not exist");
    }

    public void deleteMember(long memberId) {
        repository.deleteById(memberId);
    }

    public MemberDto getMember(long memberId) {
        var optionalMember = repository.findById(memberId);
        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            MemberDto dto = MemberDto.of(member);

            return dto;
        }

        throw new RuntimeException("Member with the given id does not exist");
    }

}
