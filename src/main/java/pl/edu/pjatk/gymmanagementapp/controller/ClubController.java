package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<ClubDto> saveClub(@RequestBody ClubDto dto) {
        return ResponseEntity.ok(clubService.saveClub(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs(){
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable long clubId, @RequestBody ClubDto dto) {
        return ResponseEntity.ok(clubService.updateClub(clubId, dto));
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<String> deleteClub(@PathVariable long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.ok("Club of id: " + clubId + " has been deleted");
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDto> getClub(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClub(clubId));
    }

    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<MemberDto>> getClubMembers(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClubMembers(clubId));
    }

    @GetMapping("/{clubId}/coaches")
    public ResponseEntity<List<CoachDto>> getClubCoaches(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClubCoaches(clubId));
    }

    @GetMapping("/{clubId}/managers")
    public ResponseEntity<List<ManagerDto>> getClubManagers(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClubManagers(clubId));
    }

    @GetMapping("/{clubId}/address")
    public ResponseEntity<AddressDto> getClubAddress(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClubAddress(clubId));
    }

    @PostMapping("/{clubId}/address")
    public ResponseEntity<AddressDto> saveClubAddress(@PathVariable long clubId, @RequestBody AddressDto dto) {
        return ResponseEntity.ok(clubService.saveClubAddress(clubId, dto));
    }


}
