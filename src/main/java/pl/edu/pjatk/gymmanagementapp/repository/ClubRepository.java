package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.gymmanagementapp.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
}
