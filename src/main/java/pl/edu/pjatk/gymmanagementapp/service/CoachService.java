package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedCoaches;
import pl.edu.pjatk.gymmanagementapp.cached.CachedMembers;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.validator.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Coach;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;

@Service

@RequiredArgsConstructor
public class CoachService {
    private final CoachRepository coachRepository;
    private final ClubRepository clubRepository;
    private final OptionalValidator optionalValidator;

    @CacheEvict(value = "ClubCoaches", allEntries = true)
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

    @CacheEvict(value = "ClubCoaches", allEntries = true)
    public CoachDto updateClubCoach(long clubId, long coachId, CoachDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        Coach coachToUpdate = optionalCoach.get();
        coachToUpdate.of(updatedDto);

        return CoachDto.of(coachRepository.save(coachToUpdate));
    }

    @CacheEvict(value = "ClubCoaches", allEntries = true)
    public void deleteClubCoach(long clubId, long coachId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalCoach = coachRepository.findById(coachId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateCoach(optionalClub, optionalCoach);

        coachRepository.delete(optionalCoach.get());
    }

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

}

