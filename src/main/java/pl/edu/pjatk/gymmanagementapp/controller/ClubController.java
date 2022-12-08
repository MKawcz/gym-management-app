package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Cleaner;
import pl.edu.pjatk.gymmanagementapp.entity.Club;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository;

    @PostMapping("/clubs")
    Club newClub (@RequestBody Club newClub){
        return clubRepository.save(newClub);
    }

    @GetMapping("/clubs")
    List<Club> listClubs(){
        return clubRepository.findAll();
    }

    @DeleteMapping("/clubs")
    ResponseEntity deleteClub(@RequestBody Long idClub){
        clubRepository.deleteById(idClub);
        return ResponseEntity.ok().build();
    }
}
