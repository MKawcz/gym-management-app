package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.entity.Club;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository repository;

    public ClubDto saveClub(ClubDto dto) {
        return ClubDto.of(repository.save(Club.of(dto)));
    }

    public List<ClubDto> getAllClubs() {
        return repository.findAll().stream().map(ClubDto::of).toList();
    }

    public ClubDto updateClub(long clubId, ClubDto updatedDto) {
        var optionalClub = repository.findById(clubId);
        if(optionalClub.isPresent()) {
            Club clubToUpdate = optionalClub.get();

            if(updatedDto.getName() != null) {
                clubToUpdate.setName(updatedDto.getName());
            }

            if(updatedDto.getStreetName() != null) {
                clubToUpdate.setStreetName(updatedDto.getStreetName());
            }

            if(updatedDto.getStreetNumber() != null) {
                clubToUpdate.setStreetNumber(updatedDto.getStreetNumber());
            }

            if(updatedDto.getPostalCode() != null) {
                clubToUpdate.setPostalCode(updatedDto.getPostalCode());
            }

            if(updatedDto.getCity() != null) {
                clubToUpdate.setCity(updatedDto.getCity());
            }

            return ClubDto.of(repository.save(clubToUpdate));
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public void deleteClub(long clubId) {
        repository.deleteById(clubId);
    }

    public ClubDto getClub(long clubId) {
        var optionalClub = repository.findById(clubId);
        if(optionalClub.isPresent()) {
            Club club = optionalClub.get();
            ClubDto dto = ClubDto.of(club);

            return dto;
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<MemberDto> getMembers(long clubId) {
        var optionalClub = repository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getMembers().stream().map(MemberDto::of).toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<CoachDto> getCoaches(long clubId) {
        var optionalClub = repository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getCoaches().stream().map(CoachDto::of).toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<ManagerDto> getManagers(long clubId) {
        var optionalClub = repository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getManagers().stream().map(ManagerDto::of).toList();
        }

        throw new RuntimeException("Club with the given id does not exist");
    }

    //    public ClubModuleDto getClubModuleData(){ //
    //      List<Club> clubList = clubRepository.findAll(); //
    //      List<ClubDto> clubDtoList = clubList.stream() //
    //                                          .map(ClubDto::of).toList(); //
    //      List<ManagerDto> managerDtoList = clubList.get(0).getManagers().stream()
    //                                                                     .map(ManagerDto::of).toList();
    //      List<CoachDto> coachDtoList = clubList.get(0).getCoaches().stream() //                .map(CoachDto::of).toList(); //        ClubDto selectedClubDto = ClubDto.of(clubList.get(0)); // //        return new ClubModuleDto(selectedClubDto, clubDtoList, managerDtoList, coachDtoList); //    } // //    public ClubModuleDto getClubModuleData(Long idClub){ //        List<Club> clubList = clubRepository.findAll(); //        List<ClubDto> clubDtoList = clubList.stream() //                .map(ClubDto::of).toList(); //        Optional<Club> optionalClub = clubList.stream().filter(x -> idClub.equals(x.getIdClub())).findFirst(); //        Club selectedClub = optionalClub.get(); //        List<ManagerDto> managerDtoList = selectedClub.getManagers().stream() //                .map(ManagerDto::of).toList(); //        List<CoachDto> coachDtoList = selectedClub.getCoaches().stream() //                .map(CoachDto::of).toList(); // //        return new ClubModuleDto(ClubDto.of(selectedClub), clubDtoList, managerDtoList, coachDtoList); //    }

}
