package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.gymmanagementapp.entity.Operator;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    public Optional<Operator> findByLogin(String login);

}
