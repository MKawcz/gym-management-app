package pl.edu.pjatk.gymmanagementapp.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.edu.pjatk.gymmanagementapp.entity.Member;
public interface ICatalogData {
    ClubRepository getClubs();
    ManagerRepository getManagers();
    CoachRepository getCoaches();
    MemberRepository getMembers();
    AddressRepository getAddresses();


}
