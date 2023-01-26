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

}
