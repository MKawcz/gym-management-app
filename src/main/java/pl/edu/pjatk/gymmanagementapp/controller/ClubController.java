package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ClubController.class);
    @PostMapping
    public ResponseEntity<ClubDto> saveClub(@Valid @RequestBody ClubDto dto) {
        ClubDto savedClub = clubService.saveClub(dto);
        log.info("Club created: {}", savedClub);
        return ResponseEntity.ok(savedClub);
    }

    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs(){
        return ResponseEntity.ok(clubService.getAllClubs().getClubs());
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable long clubId, @Valid @RequestBody ClubDto dto) {
        ClubDto updatedClub = clubService.updateClub(clubId, dto);
        log.info("Club of id" + clubId + "updated: {}", updatedClub);
        return ResponseEntity.ok(updatedClub);
    }

    @DeleteMapping("/{clubId}")
    public ResponseEntity<String> deleteClub(@PathVariable long clubId) {
        clubService.deleteClub(clubId);
        log.info("Club of id: " + clubId + " has been deleted");
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
        AddressDto savedAddress = clubService.saveClubAddress(clubId, dto);
        log.info("Address saved {} for club with id: " + clubId, savedAddress);
        return ResponseEntity.ok(savedAddress);
    }

    @PutMapping("/{clubId}/assignMember/")
    public ResponseEntity<List<MemberDto>> assignMemberToClub(@PathVariable long clubId, @RequestParam long memberId) {
        List<MemberDto> refreshedMembersList = clubService.assignMemberToClub(clubId, memberId);
        log.info("Member of id: " + memberId + " assigned to Club of id: " + clubId);
        return ResponseEntity.ok(refreshedMembersList);
    }

    @PutMapping("/{clubId}/removeMember/")
    public ResponseEntity<List<MemberDto>> removeMemberFromClub(@PathVariable long clubId, @RequestParam long memberId) {
        List<MemberDto> refreshedMembersList = clubService.removeMemberFromClub(clubId, memberId);
        log.info("Member of id: " + memberId + " removed from Club of id: " + clubId);
        return ResponseEntity.ok(refreshedMembersList);
    }

}
