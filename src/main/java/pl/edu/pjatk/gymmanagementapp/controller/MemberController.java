package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/clubs/{clubId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    @PostMapping
    public ResponseEntity<MemberDto> saveMember(@PathVariable long clubId, @Valid @RequestBody MemberDto dto) {
        MemberDto savedMember = memberService.saveMember(clubId, dto);
        log.info("Member created: {} in the Club of id: " + clubId, savedMember);
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getClubMembers(@PathVariable long clubId){
        return ResponseEntity.ok(memberService.getClubMembers(clubId).getMembers());
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto> updateClubMember(@PathVariable long clubId, @PathVariable long memberId, @Valid @RequestBody MemberDto dto) {
        MemberDto updatedMember = memberService.updateClubMember(clubId, memberId, dto);
        log.info("Member of id: " + memberId + " updated: {} in the Club of id: " + clubId, updatedMember);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteClubMember(@PathVariable long clubId, @PathVariable long memberId) {
        memberService.deleteClubMember(clubId, memberId);
        log.info("Member of id: " + memberId + " has been deleted from Club of id: " + clubId);
        return ResponseEntity.ok("Member of id:" + memberId + " has been deleted");
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getClubMember(@PathVariable long clubId, @PathVariable long memberId) {
        return ResponseEntity.ok(memberService.getClubMember(clubId, memberId));
    }

    @PutMapping("/assignMember/{memberId}")
    public ResponseEntity<List<MemberDto>> assignMemberToClub(@PathVariable long clubId, @PathVariable long memberId) {
        List<MemberDto> refreshedMembersList = memberService.assignMemberToClub(clubId, memberId);
        log.info("Member of id: " + memberId + " assigned to Club of id: " + clubId);
        return ResponseEntity.ok(refreshedMembersList);
    }

    @PutMapping("/removeMember/{memberId}")
    public ResponseEntity<List<MemberDto>> removeMemberFromClub(@PathVariable long clubId, @PathVariable long memberId) {
        List<MemberDto> refreshedMembersList = memberService.removeMemberFromClub(clubId, memberId);
        log.info("Member of id: " + memberId + " removed from Club of id: " + clubId);
        return ResponseEntity.ok(refreshedMembersList);
    }

    @PutMapping("/{memberId}/assignCoachMember/{coachId}")
    public ResponseEntity<List<MemberDto>> assignMemberToCoach(@PathVariable long clubId, @PathVariable long memberId, @PathVariable long coachId) {
        List<MemberDto> refreshedCoachMembersList  = memberService.assignMemberToCoach(clubId, coachId, memberId);
        log.info("Member of id: " + memberId + " assigned to Coach of id: " + coachId + " in the Club of id: " + clubId);
        return ResponseEntity.ok(refreshedCoachMembersList);
    }

    @PutMapping("/{memberId}/removeCoachMember/{coachId}")
    public ResponseEntity<List<MemberDto>> removeMemberFromCoach(@PathVariable long clubId, @PathVariable long memberId, @PathVariable long coachId) {
        List<MemberDto> refreshedCoachMembersList = memberService.removeMemberFromCoach(clubId, coachId, memberId);
        log.info("Member of id: " + memberId + " assigned to Coach of id: " + coachId + " in the Club of id: " + clubId);
        return ResponseEntity.ok(refreshedCoachMembersList);
    }
}
