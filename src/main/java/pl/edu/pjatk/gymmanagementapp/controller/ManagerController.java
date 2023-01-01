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

    @DeleteMapping("/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable long managerId) {
        managerService.deleteManager(managerId);
        return ResponseEntity.ok("Manager of id: " + managerId + " has been deleted");
    }

    @GetMapping("/{managerId}")
    public ResponseEntity<ManagerDto> getManager(@PathVariable long managerId) {
        return ResponseEntity.ok(managerService.getManager(managerId));
    }

}
