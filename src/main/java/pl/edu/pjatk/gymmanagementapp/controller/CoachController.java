package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CoachController.class);

    @PostMapping
    public ResponseEntity<CoachDto> saveCoach(@PathVariable long clubId, @Valid @RequestBody CoachDto dto) {
        CoachDto savedCoach = coachService.saveCoach(clubId, dto);
        log.info("Coach created: {} in the Club of id: " + clubId, savedCoach);
        return ResponseEntity.ok(savedCoach);
    }

    @GetMapping
    public ResponseEntity<List<CoachDto>> getClubCoaches(@PathVariable long clubId) {
        return ResponseEntity.ok(coachService.getClubCoaches(clubId).getCoaches());
    }

    @PutMapping("/{coachId}")
    public ResponseEntity<CoachDto> updateCoach(@PathVariable long clubId, @PathVariable long coachId, @Valid @RequestBody CoachDto dto) {
        CoachDto updatedCoach = coachService.updateClubCoach(clubId, coachId, dto);
        log.info("Coach of id: " + coachId + " updated: {} in the Club of id: " + clubId, updatedCoach);
        return ResponseEntity.ok(updatedCoach);
    }

    @DeleteMapping("/{coachId}")
    public ResponseEntity<String> deleteCoach(@PathVariable long clubId, @PathVariable long coachId) {
        coachService.deleteClubCoach(clubId, coachId);
        log.info("Coach of id: " + coachId + " has been deleted from Club of id: " + clubId);
        return ResponseEntity.ok("Coach of id: " + coachId + " has been deleted");
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<CoachDto> getCoach(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getClubCoach(clubId, coachId));
    }

    @GetMapping("/{coachId}/coachMembers")
    public ResponseEntity<List<MemberDto>> getCoachMembers(@PathVariable long clubId, @PathVariable long coachId) {
        return ResponseEntity.ok(coachService.getCoachMembers(clubId, coachId).getMembers());
    }
}