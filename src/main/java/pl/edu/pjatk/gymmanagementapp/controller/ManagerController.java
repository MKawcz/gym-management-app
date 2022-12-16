package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerRepository managerRepository;

    @PostMapping("/managers")
    public ManagerDto saveOrUpdateManager(@RequestBody ManagerDto dto){
        if(dto.getIdManager() == null){
            return ManagerDto.of(managerRepository.save(Manager.of(dto)));
        } else {
            Optional<Manager> optionalManager = managerRepository.findById(dto.getIdManager());
            if(optionalManager.isPresent()){
                Manager manager = optionalManager.get();
                manager.updateManager(dto);
                return ManagerDto.of(managerRepository.save(manager));
            } else {
                throw new RuntimeException("The user with the given id could not be found: " + dto.getIdManager());
                //todo zaimplementuj własny wyjątek i obsługę
            }
        }
    }

    @GetMapping("/managers")
    public List<ManagerDto> listManagers(){
        return managerRepository.findAll()
                .stream()
                .map(ManagerDto::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/managers/{idManager}")
    public ManagerDto getManager(@PathVariable Long idManager){
        Optional<Manager> optionalManager = managerRepository.findById(idManager);

        return ManagerDto.of(optionalManager.get());

        //todo obsłuż wyjątek, gdyby nie było managera o podanym id
    }

    @DeleteMapping("/managers/{idManager}")
    public ResponseEntity deleteManager(@PathVariable Long idManager){
        managerRepository.deleteById(idManager);
        return ResponseEntity.ok().build();
    }

}
