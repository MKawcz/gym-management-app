package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ICatalogData;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ClubRepository clubRepository;

    public ManagerDto saveManager(long clubId, ManagerDto dto) {
        Manager manager = new Manager();
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            manager.of(dto);
            manager.setClub(optionalClub.get());
            optionalClub.get().getManagers().add(manager);
            clubRepository.save(optionalClub.get());
            return ManagerDto.of(managerRepository.save(manager));
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

    public List<ManagerDto> getClubManagers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);
        if(optionalClub.isPresent()) {
            return optionalClub.get().getManagers().stream()
                    .map(ManagerDto::of)
                    .toList();
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

    public ManagerDto updateManager(long clubId, long managerId, ManagerDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);
        if(optionalClub.isPresent()) {
            if (optionalManager.isPresent()) {
                if(optionalClub.get().getManagers().contains(optionalManager.get())) {
                    Manager managerToUpdate = optionalManager.get();
                    managerToUpdate.of(updatedDto);
                    return ManagerDto.of(managerRepository.save(managerToUpdate));
                }
                throw new RuntimeException("This Club does not have a manager with the given id");
            }
            throw new RuntimeException("Manager with the given id does not exist");
        }
        throw new RuntimeException("Club with the given id does not exist");
    }
    //todo wydziel te if'y do oddzielnej funkcji
    public void deleteManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);
        if(optionalClub.isPresent()) {
            if(optionalManager.isPresent()) {
                if (optionalClub.get().getManagers().contains(optionalManager.get())) {
                    managerRepository.delete(optionalManager.get());
                }
                throw new RuntimeException("This Club does not have a manager with the given id");
            }
            throw new RuntimeException("Manager with the given id does not exist");
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

    public ManagerDto getManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);
        if(optionalClub.isPresent()) {
            if(optionalManager.isPresent()) {
                if (optionalClub.get().getManagers().contains(optionalManager.get())) {
                    Manager manager = optionalManager.get();
                    ManagerDto dto = ManagerDto.of(manager);
                    return dto;
                }
                throw new RuntimeException("This Club does not have a manager with the given id");
            }
            throw new RuntimeException("Manager with the given id does not exist");
        }
        throw new RuntimeException("Club with the given id does not exist");
    }

}
