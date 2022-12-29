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
    public ResponseEntity<List<CoachDto>> getAllCoaches(){
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
    public ResponseEntity<List<MemberDto>> getMembers(@PathVariable long coachId) {
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

//    @PostMapping("/coaches")
//    public CoachDto saveOrUpdateCoach (@RequestBody CoachDto dto){
//        if(dto.getIdCoach() == null){
//            return CoachDto.of(coachRepository.save(Coach.of(dto)));
//        } else {
//            Optional<Coach> optionalCoach = coachRepository.findById(dto.getIdCoach());
//            if(optionalCoach.isPresent()){
//                Coach coach = optionalCoach.get();
//                coach.updateCoach(dto);
//                return CoachDto.of(coachRepository.save(coach));
//            } else {
//                throw new RuntimeException("The user with the given id could not be found: " + dto.getIdCoach());
//                //todo
//                // zaimplementuj własny wyjątek
//            }
//        }
//    }
//
//    @GetMapping("/coaches")
//    public List<CoachDto> listCoaches(){
//        return coachRepository.findAll()
//                .stream()
//                .map(CoachDto::of)
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/coaches/{idCoach}")
//    public CoachDto getCoach(@PathVariable Long idCoach){
//        Optional<Coach> optionalCoach = coachRepository.findById(idCoach);
//
//        return CoachDto.of(optionalCoach.get());
//
//        //todo obsłuż wyjątek, gdyby nie było trenera o podanym id
//    }
//
//    @DeleteMapping("/coaches")
//    public ResponseEntity deleteCoach(@RequestBody Long idCoach){
//        coachRepository.deleteById(idCoach);
//        return ResponseEntity.ok().build();
//    }

}
