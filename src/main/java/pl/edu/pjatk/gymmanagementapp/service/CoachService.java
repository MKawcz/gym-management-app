package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedCoaches;
import pl.edu.pjatk.gymmanagementapp.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.exception.CoachNotFoundException;
import pl.edu.pjatk.gymmanagementapp.exception.CoachWithoutChosenMemberException;
import pl.edu.pjatk.gymmanagementapp.exception.MemberAlreadyWithCoachException;
import pl.edu.pjatk.gymmanagementapp.handler.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Club;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;



@Service
@Slf4j
@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final OptionalValidator optionalValidator;


    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public CoachDto saveCoach(long clubId, CoachDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

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

        optionalValidator.validateClub(optionalClub);

        return new CachedCoaches(optionalClub.get().getCoaches().stream()
                .map(CoachDto::of)
                .toList());
    }

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public CoachDto updateClubCoach(long clubId, long coachId, CoachDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        Coach coachToUpdate = optionalCoach.get();
        coachToUpdate.of(updatedDto);

        return CoachDto.of(coachRepository.save(coachToUpdate));
    }

    @CacheEvict(value = {"ClubCoaches", "ClubCoach"}, allEntries = true)
    public void deleteClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        coachRepository.delete(optionalCoach.get());
    }

    @Cacheable(value = "ClubCoach", key = "#coachId")
    public CoachDto getClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        Coach coach = optionalCoach.get();
        CoachDto dto = CoachDto.of(coach);

        return dto;
    }

    @Cacheable(value = "CoachMembers", key = "#coachId")
    public CachedMembers getCoachMembers(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        return new CachedMembers(optionalCoach.get().getMembers().stream()
                .map(MemberDto::of)
                .toList());
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public List<MemberDto> assignMemberToCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);
        optionalValidator.validateMember(optionalClub, optionalMember);

        if (optionalMember.get().getCoach() != null) {
            throw new MemberAlreadyWithCoachException("This member already has a coach");
        }

        optionalMember.get().setCoach(optionalCoach.get());
        optionalCoach.get().getMembers().add(optionalMember.get());
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }

    @CacheEvict(value = "CoachMembers", allEntries = true)
    public List<MemberDto> removeMemberFromCoach(long clubId, long coachId, long memberId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);
        var optionalMember = memberRepository.findById(memberId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);
        optionalValidator.validateMember(optionalClub, optionalMember);

        if (!optionalCoach.get().getMembers().contains(optionalMember.get())){
            throw new CoachWithoutChosenMemberException("This coach does not have a member with the given id");
        }

        optionalCoach.get().getMembers().remove(optionalMember.get());
        optionalMember.get().setCoach(null);
        Coach updatedCoach = coachRepository.save(optionalCoach.get());
        memberRepository.save(optionalMember.get());

        return updatedCoach.getMembers().stream().map(MemberDto::of).toList();
    }


}

