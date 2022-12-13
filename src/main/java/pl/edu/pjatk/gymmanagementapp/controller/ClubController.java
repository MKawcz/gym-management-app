package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ClubModuleDto;
import pl.edu.pjatk.gymmanagementapp.entity.Club;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.service.ClubService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ClubController {

    private final ClubRepository clubRepository;
    private final ClubService clubService;

    @PostMapping("/clubs")
    public Club newClub (@RequestBody Club newClub){
        return clubRepository.save(newClub);
    }

    @GetMapping("/clubs")
    public List<Club> listClubs(){
        return clubRepository.findAll();
    }

    @DeleteMapping("/clubs")
    public ResponseEntity deleteClub(@RequestBody Long idClub){
        clubRepository.deleteById(idClub);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/club_module_data")
    public ClubModuleDto getClubModuleData(@RequestParam Optional<String> idClub){
        if(idClub.isPresent()){
            return clubService.getClubModuleData(Long.parseLong(idClub.get()));
        } else {
            return clubService.getClubModuleData();
        }
    }
}
