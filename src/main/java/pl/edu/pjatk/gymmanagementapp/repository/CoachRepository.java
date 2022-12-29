package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.gymmanagementapp.entity.Coach;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

}
