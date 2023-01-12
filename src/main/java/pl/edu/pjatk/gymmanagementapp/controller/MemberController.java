package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/clubs/{clubId}/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto> saveMember(@PathVariable long clubId, @Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.saveMember(clubId, dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getClubMembers(@PathVariable long clubId){
        return ResponseEntity.ok(memberService.getClubMembers(clubId).getMembers());
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto> updateClubMember(@PathVariable long clubId, @PathVariable long memberId, @Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.updateClubMember(clubId, memberId, dto));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteClubMember(@PathVariable long clubId, @PathVariable long memberId) {
        memberService.deleteClubMember(clubId, memberId);
        return ResponseEntity.ok("Member of id:" + memberId + " has been deleted");
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getClubMember(@PathVariable long clubId, @PathVariable long memberId) {
        return ResponseEntity.ok(memberService.getClubMember(clubId, memberId));
    }

}
