package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.CoachService;

import java.util.List;


@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PostMapping("/{clubId}/coaches")
    public ResponseEntity<CoachDto> saveCoach(@PathVariable long clubId, @RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.saveCoach(clubId, dto));
    }

    @GetMapping("/{clubId}/coaches")
    public ResponseEntity<List<CoachDto>> getAllCoaches(@PathVariable long clubId) {
        return ResponseEntity.ok(coachService.getAllCoaches(clubId));
    }

    @PutMapping("/{clubId}/coaches/{coachId}")
    public ResponseEntity<CoachDto> updateCoach(@PathVariable long clubId, @PathVariable long coachId, @RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.updateCoach(clubId, coachId, dto));
    }

    @DeleteMapping("/{clubId}/coaches/{coachId}")
    public ResponseEntity<String> deleteCoach(@PathVariable long clubId, @PathVariable long coachId) {
        coachService.deleteCoach(clubId, coachId);
        return ResponseEntity.ok("Coach of id: " + coachId + " has been deleted");
    }

    @GetMapping("/{clubId}/coaches/{coachId}")
    public ResponseEntity<CoachDto> getCoach(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getCoach(clubId, coachId));
    }

    @GetMapping("/{clubId}/coaches/{coachId}/members")
    public ResponseEntity<List<MemberDto>> getCoachMembers(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getMembers(clubId, coachId));
    }

//    @PostMapping("/{coachId}/members")
//    public ResponseEntity<MemberDto> saveCoachNewMember(@PathVariable long coachId, @RequestBody MemberDto dto) {
//        return ResponseEntity.ok(coachService.saveCoachNewMember(coachId, dto));
//    }

    @PostMapping("/{clubId}/coaches/{coachId}/members/{memberId}")
    public ResponseEntity<MemberDto> saveCoachExistingMember(@PathVariable long clubId, @PathVariable long coachId, @PathVariable long memberId) {
        return ResponseEntity.ok(coachService.saveCoachExistingMember(clubId, coachId, memberId));
    }

    @PutMapping("/{clubId}/coaches/{coachId}/{coachId}/members/{memberId}")
    public ResponseEntity<List<MemberDto>> removeCoachMember(@PathVariable long clubId, @PathVariable long coachId, @PathVariable long memberId) {
        return ResponseEntity.ok(coachService.removeCoachMember(clubId, coachId, memberId));
    }

}