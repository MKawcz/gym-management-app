package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.gymmanagementapp.entity.Cleaner;


public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
}
