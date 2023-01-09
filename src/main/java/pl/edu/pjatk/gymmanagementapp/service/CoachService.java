package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public CoachDto saveCoach(long clubId, CoachDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        Coach coach = new Coach();
        coach.of(dto);
        coach.setClub(optionalClub.get());
        optionalClub.get().getCoaches().add(coach);
        clubRepository.save(optionalClub.get());

        return CoachDto.of(coachRepository.save(coach));
    }

    @Cacheable(value = "ClubCoaches")
    public List<CoachDto> getClubCoaches(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        return optionalClub.get().getCoaches().stream()
                .map(CoachDto::of)
                .toList();
    }

    @CachePut(value = "ClubCoach", key = "#result.idCoach")
    public CoachDto updateClubCoach(long clubId, long coachId, CoachDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }
        Coach coachToUpdate = optionalCoach.get();
        coachToUpdate.of(updatedDto);

        return CoachDto.of(coachRepository.save(coachToUpdate));
    }

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public void deleteClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }
        coachRepository.delete(optionalCoach.get());
    }

    @Cacheable(value = "ClubCoach")
    public CoachDto getClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }

        Coach coach = optionalCoach.get();
        CoachDto dto = CoachDto.of(coach);

        return dto;
    }

    @Cacheable(value = "CoachMembers")
    public List<MemberDto> getCoachMembers(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }

        return optionalCoach.get().getMembers().stream()
                .map(MemberDto::of)
                .toList();
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public MemberDto assignMemberToCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (optionalMember.get().getCoach() != null) {
            throw new RuntimeException("This member already has a coach");
        }
        if (optionalCoach.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This coach already has a member with the given id");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This Club does not have a member with the given id");
        }
        optionalMember.get().setCoach(optionalCoach.get());
        optionalCoach.get().getMembers().add(optionalMember.get());
        coachRepository.save(optionalCoach.get());

        return MemberDto.of(memberRepository.save(optionalMember.get()));
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public List<MemberDto> removeMemberFromCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalCoach.isEmpty()) {
            throw new RuntimeException("Coach with the given id does not exist");
        }
        if (optionalMember.isEmpty()) {
            throw new RuntimeException("Member with the given id does not exist");
        }
        if (!optionalCoach.get().getMembers().contains(optionalMember.get())){
            throw new RuntimeException("This coach does not have a member with the given id");
        }
        if (optionalMember.get().getCoach().getIdCoach() != coachId) {
            throw new RuntimeException("This member does not have a coach with the given id");
        }
        if (!optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new RuntimeException("This Club does not have a coach with the given id");
        }
        if (!optionalClub.get().getMembers().contains(optionalMember.get())) {
            throw new RuntimeException("This Club does not have a member with the given id");
        }

        optionalCoach.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setCoach(null);
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }

}

