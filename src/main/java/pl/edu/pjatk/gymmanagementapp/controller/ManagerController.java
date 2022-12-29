package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerDto> saveManager(@RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.saveManager(dto));
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers(){
        return ResponseEntity.ok(managerService.getAllManagers());
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable long managerId, @RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.updateManager(managerId, dto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteManager(@RequestParam long managerId) {
        managerService.deleteManager(managerId);
        return ResponseEntity.ok("Manager of id: " + managerId + " has been deleted");
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDto> getManager(@PathVariable long managerId) {
        return ResponseEntity.ok(managerService.getManager(managerId));
    }

//    @PostMapping("/managers")
//    public ManagerDto saveOrUpdateManager(@RequestBody ManagerDto dto){
//        if(dto.getIdManager() == null){
//            return ManagerDto.of(managerRepository.save(Manager.of(dto)));
//        } else {
//            Optional<Manager> optionalManager = managerRepository.findById(dto.getIdManager());
//            if(optionalManager.isPresent()){
//                Manager manager = optionalManager.get();
//                manager.updateManager(dto);
//                return ManagerDto.of(managerRepository.save(manager));
//            } else {
//                throw new RuntimeException("The user with the given id could not be found: " + dto.getIdManager());
//                //todo zaimplementuj własny wyjątek i obsługę
//            }
//        }
//    }
//
//    @GetMapping("/managers")
//    public List<ManagerDto> listManagers(){
//        return managerRepository.findAll()
//                .stream()
//                .map(ManagerDto::of)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/managers/{idManager}")
//    public ManagerDto getManager(@PathVariable Long idManager){
//        Optional<Manager> optionalManager = managerRepository.findById(idManager);
//
//        return ManagerDto.of(optionalManager.get());
//
//        //todo obsłuż wyjątek, gdyby nie było managera o podanym id
//    }
//
//    @DeleteMapping("/managers/{idManager}")
//    public ResponseEntity deleteManager(@PathVariable Long idManager){
//        managerRepository.deleteById(idManager);
//        return ResponseEntity.ok().build();
//    }

}
