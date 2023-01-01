package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.CoachService;

import java.util.List;


@RestController
@RequestMapping("/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @PostMapping
    public ResponseEntity<CoachDto> saveCoach(@RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.saveCoach(dto));
    }

    @GetMapping
    public ResponseEntity<List<CoachDto>> getAllCoaches() {
        return ResponseEntity.ok(coachService.getAllCoaches());
    }

    @PutMapping("/{coachId}")
    public ResponseEntity<CoachDto> updateCoach(@PathVariable long coachId, @RequestBody CoachDto dto) {
        return ResponseEntity.ok(coachService.updateCoach(coachId, dto));
    }

    @DeleteMapping("/{coachId}")
    public ResponseEntity<String> deleteCoach(@PathVariable long coachId) {
        coachService.deleteCoach(coachId);
        return ResponseEntity.ok("Coach of id: " + coachId + " has been deleted");
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<CoachDto> getCoach(@PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getCoach(coachId));
    }

    @GetMapping("/{coachId}/members")
    public ResponseEntity<List<MemberDto>> getCoachMembers(@PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getMembers(coachId));
    }

    @PostMapping("/{coachId}/members")
    public ResponseEntity<MemberDto> saveCoachNewMember(@PathVariable long coachId, @RequestBody MemberDto dto) {
        return ResponseEntity.ok(coachService.saveCoachNewMember(coachId, dto));
    }

    @PostMapping("/{coachId}/members/{memberId}")
    public ResponseEntity<MemberDto> saveCoachExistingMember(@PathVariable long coachId, @PathVariable long memberId) {
        return ResponseEntity.ok(coachService.saveCoachExistingMember(coachId, memberId));
    }

    @PutMapping("/{coachId}/members/{memberId}")
    public ResponseEntity<List<MemberDto>> removeCoachMember(@PathVariable long coachId, @PathVariable long memberId) {
        return ResponseEntity.ok(coachService.removeCoachMember(coachId, memberId));
    }

}