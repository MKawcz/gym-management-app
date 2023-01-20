package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/clubs/{clubId}/managers")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerDto> saveManager(@PathVariable long clubId, @Valid @RequestBody ManagerDto dto) {
        ManagerDto savedManager = managerService.saveManager(clubId, dto);
        log.info("Manager created: {} in the Club of id: " + clubId, savedManager);
        return ResponseEntity.ok(savedManager);
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers(@PathVariable long clubId){
        return ResponseEntity.ok(managerService.getClubManagers(clubId).getManagers());
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable long clubId, @PathVariable long managerId, @Valid @RequestBody ManagerDto dto) {
        ManagerDto updatedManager = managerService.updateClubManager(clubId, managerId, dto);
        log.info("Manager of id" + managerId + " updated: {} in the Club of id: " + clubId, updatedManager);
        return ResponseEntity.ok(updatedManager);
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable long clubId, @PathVariable long managerId) {
        managerService.deleteClubManager(clubId, managerId);
        log.info("Manager of id: " + managerId + " has been deleted from Club of id: " + clubId);
        return ResponseEntity.ok("Manager of id: " + managerId + " has been deleted");
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDto> getManager(@PathVariable long clubId, @PathVariable long managerId) {
        return ResponseEntity.ok(managerService.getClubManager(clubId, managerId));
    }

}
