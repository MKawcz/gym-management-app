package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/{clubId}/managers")
    public ResponseEntity<ManagerDto> saveManager(@PathVariable long clubId, @RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.saveManager(clubId, dto));
    }

    @GetMapping("/{clubId}/managers")
    public ResponseEntity<List<ManagerDto>> getAllManagers(@PathVariable long clubId){
        return ResponseEntity.ok(managerService.getClubManagers(clubId));
    }

    @PutMapping("/{clubId}/managers/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable long clubId, @PathVariable long managerId, @RequestBody ManagerDto dto) {
        return ResponseEntity.ok(managerService.updateManager(clubId, managerId, dto));
    }

    @DeleteMapping("/{clubId}/managers/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable long clubId, @PathVariable long managerId) {
        managerService.deleteManager(clubId, managerId);
        return ResponseEntity.ok("Manager of id: " + managerId + " has been deleted");
    }

    @GetMapping("/{clubId}/managers/{managerId}")
    public ResponseEntity<ManagerDto> getManager(@PathVariable long clubId, @PathVariable long managerId) {
        return ResponseEntity.ok(managerService.getManager(clubId, managerId));
    }

}
