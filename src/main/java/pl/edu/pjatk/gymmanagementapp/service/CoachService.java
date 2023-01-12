package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.anntotation.cached.CachedCoaches;
import pl.edu.pjatk.gymmanagementapp.anntotation.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.exception.NoSuchEntityInChosenClubException;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.model.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import static pl.edu.pjatk.gymmanagementapp.service.ClubService.validateClub;
import static pl.edu.pjatk.gymmanagementapp.service.MemberService.validateMember;

@Service
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public CoachDto saveCoach(long clubId, CoachDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        Coach coach = new Coach();
        coach.of(dto);
        coach.setClub(optionalClub.get());
        optionalClub.get().getCoaches().add(coach);
        clubRepository.save(optionalClub.get());

        return CoachDto.of(coachRepository.save(coach));
    }

    @Cacheable(value = "ClubCoaches")
    public CachedCoaches getClubCoaches(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        validateClub(optionalClub);

        return new CachedCoaches(optionalClub.get().getCoaches().stream()
                .map(CoachDto::of)
                .toList());
    }

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public CoachDto updateClubCoach(long clubId, long coachId, CoachDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);

        Coach coachToUpdate = optionalCoach.get();
        coachToUpdate.of(updatedDto);

        return CoachDto.of(coachRepository.save(coachToUpdate));
    }

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public void deleteClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);

        coachRepository.delete(optionalCoach.get());
    }

    @Cacheable(value = "ClubCoach", key = "#coachId")
    public CoachDto getClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);

        Coach coach = optionalCoach.get();
        CoachDto dto = CoachDto.of(coach);

        return dto;
    }

    @Cacheable(value = "CoachMembers", key = "#coachId")
    public CachedMembers getCoachMembers(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);

        return new CachedMembers(optionalCoach.get().getMembers().stream()
                .map(MemberDto::of)
                .toList());
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public MemberDto assignMemberToCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);
        validateMember(optionalClub, optionalMember);

        if (optionalMember.get().getCoach() != null) {
            throw new RuntimeException("This member already has a coach");          //todo custom exc
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

        validateClub(optionalClub);
        validateCoach(optionalClub, optionalCoach);
        validateMember(optionalClub, optionalMember);

        if (!optionalCoach.get().getMembers().contains(optionalMember.get())){
            throw new RuntimeException("This coach does not have a member with the given id");          //todo custom exc
        }

        optionalCoach.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setCoach(null);
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }

    private static void validateCoach(Optional<Club> optionalClub, Optional<Coach> optionalCoach) {
        if (optionalCoach.isEmpty() || !optionalClub.get().getCoaches().contains(optionalCoach.get())) {
            throw new NoSuchEntityInChosenClubException("This Club does not have a coach with the given id");
        }
    }

}

