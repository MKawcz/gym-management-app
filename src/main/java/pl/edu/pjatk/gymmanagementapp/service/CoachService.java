package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;

    public CoachDto saveCoach(CoachDto dto) {
        return CoachDto.of(coachRepository.save(Coach.of(dto)));
    }

    public List<CoachDto> getAllCoaches() {
        return coachRepository.findAll().stream().map(CoachDto::of).toList();
    }

    public CoachDto updateCoach(long coachId, CoachDto updatedDto) {
        var optionalCoach = coachRepository.findById(coachId);
        if(optionalCoach.isPresent()) {
            Coach coachToUpdate = optionalCoach.get();

            if(updatedDto.getFirstName() != null) {
                coachToUpdate.setFirstName(updatedDto.getFirstName());
            }

            if(updatedDto.getLastName() != null) {
                coachToUpdate.setLastName(updatedDto.getLastName());
            }

            if(updatedDto.getSalary() != null) {
                coachToUpdate.setSalary(updatedDto.getSalary());
            }

            return CoachDto.of(coachRepository.save(coachToUpdate));
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public void deleteCoach(long coachId) {
        coachRepository.deleteById(coachId);
    }

    public CoachDto getCoach(long coachId) {
        var optionalCoach = coachRepository.findById(coachId);
        if(optionalCoach.isPresent()) {
            Coach coach = optionalCoach.get();
            CoachDto dto = CoachDto.of(coach);

            return dto;
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public List<MemberDto> getMembers(long coachId) {
        var optionalCoach = coachRepository.findById(coachId);
        if(optionalCoach.isPresent()) {
            return optionalCoach.get().getMembers().stream().map(MemberDto::of).toList();
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public MemberDto saveCoachNewMember(long coachId, MemberDto dto) {
        var optionalCoach = coachRepository.findById(coachId);
        if(optionalCoach.isPresent()) {
            Member member = Member.of(dto);
            member.setCoach(optionalCoach.get());
            member.setClub(optionalCoach.get().getClub());
            return MemberDto.of(memberRepository.save(member));
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public MemberDto saveCoachExistingMember(long coachId, long memberId) {
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        if(optionalCoach.isPresent()) {
            if (optionalMember.isPresent()) {
                optionalMember.get().setCoach(optionalCoach.get());
                optionalMember.get().setClub(optionalCoach.get().getClub());
                return MemberDto.of(memberRepository.save(optionalMember.get()));
            }
            throw new RuntimeException("Member with the given id does not exist");
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }
}

