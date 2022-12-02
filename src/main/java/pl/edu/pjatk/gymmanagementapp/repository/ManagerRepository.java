package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.gymmanagementapp.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
