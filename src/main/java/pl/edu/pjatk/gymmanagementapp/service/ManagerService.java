package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.cached.CachedManagers;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.validator.OptionalValidator;
import pl.edu.pjatk.gymmanagementapp.model.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ClubRepository;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;


@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final ClubRepository clubRepository;
    private final OptionalValidator optionalValidator;


    @CacheEvict(value = "ClubManagers", allEntries = true)
    public ManagerDto saveManager(long clubId, ManagerDto dto) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        Manager manager = new Manager();
        manager.of(dto);
        manager.setClub(optionalClub.get());
        optionalClub.get().getManagers().add(manager);
        clubRepository.save(optionalClub.get());

        return ManagerDto.of(managerRepository.save(manager));
    }

    @Cacheable(value = "ClubManagers")
    public CachedManagers getClubManagers(long clubId) {
        var optionalClub = clubRepository.findById(clubId);

        optionalValidator.validateClub(optionalClub);

        return new CachedManagers(optionalClub.get().getManagers().stream()
                .map(ManagerDto::of)
                .toList());
    }

    @CacheEvict(value = "ClubManagers", allEntries = true)
    public ManagerDto updateClubManager(long clubId, long managerId, ManagerDto updatedDto) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateManager(optionalClub, optionalManager);

        Manager managerToUpdate = optionalManager.get();
        managerToUpdate.of(updatedDto);

        return ManagerDto.of(managerRepository.save(managerToUpdate));
    }


    @CacheEvict(value = "ClubManagers", allEntries = true)
    public void deleteClubManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateManager(optionalClub, optionalManager);

        managerRepository.delete(optionalManager.get());
    }

    public ManagerDto getClubManager(long clubId, long managerId) {
        var optionalClub = clubRepository.findById(clubId);
        var optionalManager = managerRepository.findById(managerId);

        optionalValidator.validateClub(optionalClub);
        optionalValidator.validateManager(optionalClub, optionalManager);

        Manager manager = optionalManager.get();
        ManagerDto dto = ManagerDto.of(manager);

        return dto;
    }

}
