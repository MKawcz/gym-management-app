package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.CoachRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CoachController {

    private final CoachRepository coachRepository;

    @PostMapping("/coaches")
    public Coach newCoach (@RequestBody Coach newCoach){
        return coachRepository.save(newCoach);
    }

    @GetMapping("/coaches")
    public List<Coach> listCoaches(){
        return coachRepository.findAll();
    }

    @DeleteMapping("/coaches")
    public ResponseEntity deleteCoach(@RequestBody Long idCoach){
        coachRepository.deleteById(idCoach);
        return ResponseEntity.ok().build();
    }

}
