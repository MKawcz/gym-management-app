package pl.edu.pjatk.gymmanagementapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.gymmanagementapp.dto.ClubDto;
import pl.edu.pjatk.gymmanagementapp.dto.CoachDto;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    @PostMapping
    public ResponseEntity<ClubDto> saveClub(@RequestBody ClubDto dto) {
        return ResponseEntity.ok(clubService.saveClub(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClubDto>> getAllClubs(){
        return ResponseEntity.ok(clubService.getAllClubs());
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable long clubId, @RequestBody ClubDto dto) {
        return ResponseEntity.ok(clubService.updateClub(clubId, dto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteClub(@RequestParam long clubId) {
        clubService.deleteClub(clubId);
        return ResponseEntity.ok("Club of id: " + clubId + " has been deleted");
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDto> getClub(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getClub(clubId));
    }

    @GetMapping("/{clubId}/members")
    public ResponseEntity<List<MemberDto>> getMembers(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getMembers(clubId));
    }

    @GetMapping("/{clubId}/coaches")
    public ResponseEntity<List<CoachDto>> getCoaches(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getCoaches(clubId));
    }

    @GetMapping("/{clubId}/managers")
    public ResponseEntity<List<ManagerDto>> getManagers(@PathVariable long clubId) {
        return ResponseEntity.ok(clubService.getManagers(clubId));
    }

//    @GetMapping("/club_module_data")
//    public ClubModuleDto getClubModuleData(@RequestParam Optional<String> idClub){
//        if(idClub.isPresent()){
//            return clubService.getClubModuleData(Long.parseLong(idClub.get()));
//        } else {
//            return clubService.getClubModuleData();
//        }
//    }


//    @PostMapping("/clubs")
//    public Club newClub (@RequestBody Club newClub){
//        return clubRepository.save(newClub);
//    }
//
//    @GetMapping("/clubs")
//    public List<Club> listClubs(){
//        return clubRepository.findAll();
//    }
//
//    @DeleteMapping("/clubs")
//    public ResponseEntity deleteClub(@RequestBody Long idClub){
//        clubRepository.deleteById(idClub);
//        return ResponseEntity.ok().build();
//    }
//

}
