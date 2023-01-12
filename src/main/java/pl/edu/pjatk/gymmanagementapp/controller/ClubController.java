package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ClubDto> saveClub(@Valid @RequestBody ClubDto dto) {
        return ResponseEntity.ok(clubService.saveClub(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs(){
        return ResponseEntity.ok(clubService.getAllClubs().getClubs());
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable long clubId, @Valid @RequestBody ClubDto dto) {
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

    @GetMapping("/{clubId}/address")
    public ResponseEntity<AddressDto> getClubAddress(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClubAddress(clubId));
    }

    @PostMapping("/{clubId}/address")
    public ResponseEntity<AddressDto> saveClubAddress(@PathVariable long clubId, @Valid @RequestBody AddressDto dto) {
        return ResponseEntity.ok(clubService.saveClubAddress(clubId, dto));
    }

    @PutMapping("/{clubId}/assignMember/")
    public ResponseEntity<List<MemberDto>> assignMemberToClub(@PathVariable long clubId, @RequestParam long memberId) {
        return ResponseEntity.ok(clubService.assignMemberToClub(clubId, memberId));
    }

    @PutMapping("/{clubId}/removeMember/")
    public ResponseEntity<List<MemberDto>> removeMemberFromClub(@PathVariable long clubId, @RequestParam long memberId) {
        return ResponseEntity.ok(clubService.removeMemberFromClub(clubId, memberId));
    }

}
