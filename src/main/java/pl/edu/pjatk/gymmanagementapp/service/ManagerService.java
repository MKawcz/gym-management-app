package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ICatalogData;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerDto saveManager(ManagerDto dto) {
        Manager manager = new Manager();
        manager.of(dto);
        return ManagerDto.of(managerRepository.save(manager));
    }

    public List<ManagerDto> getAllManagers() {
        return managerRepository.findAll().stream()
                .map(ManagerDto::of)
                .toList();
    }

    public ManagerDto updateManager(long managerId, ManagerDto updatedDto) {
        var optionalManager = managerRepository.findById(managerId);
        if(optionalManager.isPresent()) {
            Manager managerToUpdate = optionalManager.get();
            managerToUpdate.of(updatedDto);
            return ManagerDto.of(managerRepository.save(managerToUpdate));
        }

        throw new RuntimeException("Manager with the given id does not exist");
    }

    public void deleteManager(long managerId) {
        managerRepository.deleteById(managerId);
    }

    public ManagerDto getManager(long managerId) {
        var optionalManager = managerRepository.findById(managerId);
        if(optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            ManagerDto dto = ManagerDto.of(manager);

            return dto;
        }

        throw new RuntimeException("Manager with the given id does not exist");
    }

}
