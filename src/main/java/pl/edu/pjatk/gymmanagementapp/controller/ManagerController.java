package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerRepository managerRepository;

    @PostMapping("/managers")
    Manager newManager (@RequestBody Manager newManager){
        return managerRepository.save(newManager);
    }

}
