package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.CoachService;

import java.util.List;


@RestController
@RequestMapping("/clubs/{clubId}/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PostMapping
    public ResponseEntity<CoachDto> saveCoach(@PathVariable long clubId, @Valid @RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.saveCoach(clubId, dto));
    }

    @GetMapping
    public ResponseEntity<List<CoachDto>> getClubCoaches(@PathVariable long clubId) {
        return ResponseEntity.ok(coachService.getClubCoaches(clubId));
    }

    @PutMapping("/{coachId}")
    public ResponseEntity<CoachDto> updateCoach(@PathVariable long clubId, @PathVariable long coachId, @Valid @RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.updateClubCoach(clubId, coachId, dto));
    }

    @DeleteMapping("/{coachId}")
    public ResponseEntity<String> deleteCoach(@PathVariable long clubId, @PathVariable long coachId) {
        coachService.deleteClubCoach(clubId, coachId);
        return ResponseEntity.ok("Coach of id: " + coachId + " has been deleted");
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<CoachDto> getCoach(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getClubCoach(clubId, coachId));
    }

    @GetMapping("/{coachId}/coachMembers")
    public ResponseEntity<List<MemberDto>> getCoachMembers(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getCoachMembers(clubId, coachId));
    }

    @PutMapping("/{coachId}/assignCoachMember/")
    public ResponseEntity<MemberDto> assignMemberToCoach(@PathVariable long clubId, @PathVariable long coachId, @RequestParam long memberId) {
        return ResponseEntity.ok(coachService.assignMemberToCoach(clubId, coachId, memberId));
    }

    @PutMapping("/{coachId}/removeCoachMember/")
    public ResponseEntity<List<MemberDto>> removeMemberFromCoach(@PathVariable long clubId, @PathVariable long coachId, @RequestParam long memberId) {
        return ResponseEntity.ok(coachService.removeMemberFromCoach(clubId, coachId, memberId));
    }

}