package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/clubs/{clubId}/managers")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerDto> saveManager(@PathVariable long clubId, @Valid @RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.saveManager(clubId, dto));
    }

    @GetMapping
    public ResponseEntity<List<ManagerDto>> getAllManagers(@PathVariable long clubId){
        return ResponseEntity.ok(managerService.getClubManagers(clubId));
    }

    @PutMapping("/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable long clubId, @PathVariable long managerId, @Valid @RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.updateClubManager(clubId, managerId, dto));
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable long clubId, @PathVariable long managerId) {
        managerService.deleteClubManager(clubId, managerId);
        return ResponseEntity.ok("Manager of id: " + managerId + " has been deleted");
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDto> getManager(@PathVariable long clubId, @PathVariable long managerId) {
        return ResponseEntity.ok(managerService.getClubManager(clubId, managerId));
    }

}
