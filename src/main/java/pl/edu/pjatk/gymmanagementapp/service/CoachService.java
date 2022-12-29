package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachService {

    private final CoachRepository repository;

    public CoachDto saveCoach(CoachDto dto) {
        return CoachDto.of(repository.save(Coach.of(dto)));
    }

    public List<CoachDto> getAllCoaches() {
        return repository.findAll().stream().map(CoachDto::of).toList();
    }

    public CoachDto updateCoach(long coachId, CoachDto updatedDto) {
        var optionalCoach = repository.findById(coachId);
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

            return CoachDto.of(repository.save(coachToUpdate));
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public void deleteCoach(long coachId) {
        repository.deleteById(coachId);
    }

    public CoachDto getCoach(long coachId) {
        var optionalCoach = repository.findById(coachId);
        if(optionalCoach.isPresent()) {
            Coach coach = optionalCoach.get();
            CoachDto dto = CoachDto.of(coach);

            return dto;
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

    public List<MemberDto> getMembers(long coachId) {
        var optionalCoach = repository.findById(coachId);
        if(optionalCoach.isPresent()) {
            return optionalCoach.get().getMembers().stream().map(MemberDto::of).toList();
        }

        throw new RuntimeException("Coach with the given id does not exist");
    }

}

