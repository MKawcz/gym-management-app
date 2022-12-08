package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Cleaner;
import pl.edu.pjatk.gymmanagementapp.repository.CleanerRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CleanerController {

    private final CleanerRepository cleanerRepository;

    @PostMapping("/cleaners")
    Cleaner newCleaner (@RequestBody Cleaner newCleaner){
        return cleanerRepository.save(newCleaner);
    }

    @GetMapping("/cleaners")
    List<Cleaner> listCleaners(){
        return cleanerRepository.findAll();
    }

    @DeleteMapping("/cleaners")
    ResponseEntity deleteCleaner(@RequestBody Long idCleaner){
        cleanerRepository.deleteById(idCleaner);
        return ResponseEntity.ok().build();
    }
}
