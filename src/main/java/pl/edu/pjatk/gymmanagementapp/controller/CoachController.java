package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final CoachRepository coachRepository;

    @PostMapping("/coaches")
    public CoachDto saveOrUpdateCoach (@RequestBody CoachDto dto){
        if(dto.getIdCoach() == null){
            return CoachDto.of(coachRepository.save(Coach.of(dto)));
        } else {
            Optional<Coach> optionalCoach = coachRepository.findById(dto.getIdCoach());
            if(optionalCoach.isPresent()){
                Coach coach = optionalCoach.get();
                coach.updateCoach(dto);
                return CoachDto.of(coachRepository.save(coach));
            } else {
                throw new RuntimeException("The user with the given id could not be found: " + dto.getIdCoach());
                //todo zaimplementuj własny wyjątek
            }
        }
    }

    @GetMapping("/coaches")
    public List<CoachDto> listCoaches(){
        return coachRepository.findAll()
                .stream()
                .map(CoachDto::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/coaches/{idCoach}")
    public CoachDto getManager(@PathVariable Long idCoach){
        Optional<Coach> optionalCoach = coachRepository.findById(idCoach);

        return CoachDto.of(optionalCoach.get());

        //todo obsłuż wyjątek, gdyby nie było trenera o podanym id
    }

    @DeleteMapping("/coaches")
    public ResponseEntity deleteCoach(@RequestBody Long idCoach){
        coachRepository.deleteById(idCoach);
        return ResponseEntity.ok().build();
    }

}
