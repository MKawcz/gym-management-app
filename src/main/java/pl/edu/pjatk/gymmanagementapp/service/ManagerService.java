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
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        Manager manager = new Manager();
        manager.of(dto);
        manager.setClub(optionalClub.get());
        optionalClub.get().getManagers().add(manager);
        clubRepository.save(optionalClub.get());

        return ManagerDto.of(managerRepository.save(manager));
    }

    public List<ManagerDto> getClubManagers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }

        return optionalClub.get().getManagers().stream()
                .map(ManagerDto::of)
                .toList();
    }

    public ManagerDto updateClubManager(long clubId, long managerId, ManagerDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalManager.isEmpty()) {
            throw new RuntimeException("Manager with the given id does not exist");
        }
        if (!optionalClub.get().getManagers().contains(optionalManager.get())) {
            throw new RuntimeException("This Club does not have a manager with the given id");
        }

        Manager managerToUpdate = optionalManager.get();
        managerToUpdate.of(updatedDto);

        return ManagerDto.of(managerRepository.save(managerToUpdate));
    }
    //todo wydziel te if'y do oddzielnej funkcji
    public void deleteClubManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalManager.isEmpty()) {
            throw new RuntimeException("Manager with the given id does not exist");
        }
        if (!optionalClub.get().getManagers().contains(optionalManager.get())) {
            throw new RuntimeException("This Club does not have a manager with the given id");
        }

        managerRepository.delete(optionalManager.get());
    }

    public ManagerDto getClubManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        if (optionalClub.isEmpty()) {
            throw new RuntimeException("Club with the given id does not exist");
        }
        if (optionalManager.isEmpty()) {
            throw new RuntimeException("Manager with the given id does not exist");
        }
        if (!optionalClub.get().getManagers().contains(optionalManager.get())) {
            throw new RuntimeException("This Club does not have a manager with the given id");
        }

        Manager manager = optionalManager.get();
        ManagerDto dto = ManagerDto.of(manager);

        return dto;
    }
}
