package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.gymmanagementapp.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
