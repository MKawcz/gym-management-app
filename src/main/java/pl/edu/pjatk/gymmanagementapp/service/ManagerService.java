package pl.edu.pjatk.gymmanagementapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.gymmanagementapp.dto.ManagerDto;
import pl.edu.pjatk.gymmanagementapp.dto.MemberDto;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;
import pl.edu.pjatk.gymmanagementapp.repository.ManagerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository repository;

    public ManagerDto saveManager(ManagerDto dto) {
        return ManagerDto.of(repository.save(Manager.of(dto)));
    }

    public List<ManagerDto> getAllManagers() {
        return repository.findAll().stream().map(ManagerDto::of).toList();
    }

    public ManagerDto updateManager(long managerId, ManagerDto updatedDto) {
        var optionalManager = repository.findById(managerId);
        if(optionalManager.isPresent()) {
            Manager managerToUpdate = optionalManager.get();

            if(updatedDto.getFirstName() != null) {
                managerToUpdate.setFirstName(updatedDto.getFirstName());
            }

            if(updatedDto.getLastName() != null) {
                managerToUpdate.setLastName(updatedDto.getLastName());
            }

            if(updatedDto.getSalary() != null) {
                managerToUpdate.setSalary(updatedDto.getSalary());
            }

            return ManagerDto.of(repository.save(managerToUpdate));
        }

        throw new RuntimeException("Manager with the given id does not exist");
    }

    public void deleteManager(long managerId) {
        repository.deleteById(managerId);
    }

    public ManagerDto getManager(long managerId) {
        var optionalManager = repository.findById(managerId);
        if(optionalManager.isPresent()) {
            Manager manager = optionalManager.get();
            ManagerDto dto = ManagerDto.of(manager);

            return dto;
        }

        throw new RuntimeException("Manager with the given id does not exist");
    }

}
