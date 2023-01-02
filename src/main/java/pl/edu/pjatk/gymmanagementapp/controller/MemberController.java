package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/{clubId}/members")
    public ResponseEntity<MemberDto> saveMember(@PathVariable long clubId, @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.saveMember(clubId, dto));
    }

    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<MemberDto>> getAllMembers(@PathVariable long clubId){
        return ResponseEntity.ok(memberService.getAllMembers(clubId));
    }

    @PutMapping("/{clubId}/members/{memberId}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable long clubId, @PathVariable long memberId, @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.updateMember(clubId, memberId, dto));
    }

    @DeleteMapping("/{clubId}/members/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable long clubId, @PathVariable long memberId) {
        memberService.deleteMember(clubId, memberId);
        return ResponseEntity.ok("Member of id:" + memberId + " has been deleted");
    }

    @GetMapping("/{clubId}/members/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable long clubId, @PathVariable long memberId) {
        return ResponseEntity.ok(memberService.getMember(clubId, memberId));
    }

}
