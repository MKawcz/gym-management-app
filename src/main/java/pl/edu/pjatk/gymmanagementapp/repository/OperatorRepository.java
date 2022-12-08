package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.gymmanagementapp.entity.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
