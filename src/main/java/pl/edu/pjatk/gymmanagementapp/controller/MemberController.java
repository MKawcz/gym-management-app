package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberDto> saveMember(@RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.saveMember(dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto> updateMember(@PathVariable long memberId, @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.updateMember(memberId, dto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestParam long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("Member of id:" + memberId + " has been deleted");
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

}
