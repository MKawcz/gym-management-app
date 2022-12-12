package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerRepository managerRepository;

    @PostMapping("/managers")
    public Manager newManager (@RequestBody Manager newManager){
        return managerRepository.save(newManager);
    }

    @GetMapping("/managers")
    public List<Manager> listManagers(){
        return managerRepository.findAll();
    }

    @DeleteMapping("/managers")
    public ResponseEntity deleteManager(@RequestBody Long idManager){
        managerRepository.deleteById(idManager);
        return ResponseEntity.ok().build();
    }

}
