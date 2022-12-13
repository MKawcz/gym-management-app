package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.*;
import pl.edu.pjatk.gymmanagementapp.entity.Assistant;
import pl.edu.pjatk.gymmanagementapp.entity.Club;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubModuleDto getClubModuleData(){
        List<Club> clubList = clubRepository.findAll();
        List<ClubDto> clubDtoList = clubList.stream()
                .map(ClubDto::of).toList();
        List<ManagerDto> managerDtoList = clubList.get(0).getManagers().stream()
                .map(ManagerDto::of).toList();
        List<CoachDto> coachDtoList = clubList.get(0).getCoaches().stream()
                .map(CoachDto::of).toList();
        List<AssistantDto> assistantDtoList = clubList.get(0).getAssistants().stream()
                .map(AssistantDto::of).toList();
        ClubDto selectedClubDto = ClubDto.of(clubList.get(0));

        return new ClubModuleDto(selectedClubDto, clubDtoList, managerDtoList, coachDtoList, assistantDtoList);
    }

    public ClubModuleDto getClubModuleData(Long idClub){
        List<Club> clubList = clubRepository.findAll();
        List<ClubDto> clubDtoList = clubList.stream()
                .map(ClubDto::of).toList();
        Optional<Club> optionalClub = clubList.stream().filter(x -> idClub.equals(x.getIdClub())).findFirst();
        Club selectedClub = optionalClub.get();
        List<ManagerDto> managerDtoList = selectedClub.getManagers().stream()
                .map(ManagerDto::of).toList();
        List<CoachDto> coachDtoList = selectedClub.getCoaches().stream()
                .map(CoachDto::of).toList();
        List<AssistantDto> assistantDtoList = selectedClub.getAssistants().stream()
                .map(AssistantDto::of).toList();

        return new ClubModuleDto(ClubDto.of(selectedClub), clubDtoList, managerDtoList, coachDtoList, assistantDtoList);
    }

}
